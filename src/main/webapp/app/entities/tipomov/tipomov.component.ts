import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipomov } from 'app/shared/model/tipomov.model';
import { AccountService } from 'app/core';
import { TipomovService } from './tipomov.service';

@Component({
  selector: 'jhi-tipomov',
  templateUrl: './tipomov.component.html'
})
export class TipomovComponent implements OnInit, OnDestroy {
  tipomovs: ITipomov[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected tipomovService: TipomovService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.tipomovService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ITipomov[]>) => res.ok),
          map((res: HttpResponse<ITipomov[]>) => res.body)
        )
        .subscribe((res: ITipomov[]) => (this.tipomovs = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.tipomovService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipomov[]>) => res.ok),
        map((res: HttpResponse<ITipomov[]>) => res.body)
      )
      .subscribe(
        (res: ITipomov[]) => {
          this.tipomovs = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTipomovs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipomov) {
    return item.id;
  }

  registerChangeInTipomovs() {
    this.eventSubscriber = this.eventManager.subscribe('tipomovListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
