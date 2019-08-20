import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMotivomov } from 'app/shared/model/motivomov.model';
import { AccountService } from 'app/core';
import { MotivomovService } from './motivomov.service';

@Component({
  selector: 'jhi-motivomov',
  templateUrl: './motivomov.component.html'
})
export class MotivomovComponent implements OnInit, OnDestroy {
  motivomovs: IMotivomov[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected motivomovService: MotivomovService,
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
      this.motivomovService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IMotivomov[]>) => res.ok),
          map((res: HttpResponse<IMotivomov[]>) => res.body)
        )
        .subscribe((res: IMotivomov[]) => (this.motivomovs = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.motivomovService
      .query()
      .pipe(
        filter((res: HttpResponse<IMotivomov[]>) => res.ok),
        map((res: HttpResponse<IMotivomov[]>) => res.body)
      )
      .subscribe(
        (res: IMotivomov[]) => {
          this.motivomovs = res;
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
    this.registerChangeInMotivomovs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMotivomov) {
    return item.id;
  }

  registerChangeInMotivomovs() {
    this.eventSubscriber = this.eventManager.subscribe('motivomovListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
