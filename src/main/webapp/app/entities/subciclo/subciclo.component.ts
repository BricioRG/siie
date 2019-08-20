import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubciclo } from 'app/shared/model/subciclo.model';
import { AccountService } from 'app/core';
import { SubcicloService } from './subciclo.service';

@Component({
  selector: 'jhi-subciclo',
  templateUrl: './subciclo.component.html'
})
export class SubcicloComponent implements OnInit, OnDestroy {
  subciclos: ISubciclo[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected subcicloService: SubcicloService,
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
      this.subcicloService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ISubciclo[]>) => res.ok),
          map((res: HttpResponse<ISubciclo[]>) => res.body)
        )
        .subscribe((res: ISubciclo[]) => (this.subciclos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.subcicloService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubciclo[]>) => res.ok),
        map((res: HttpResponse<ISubciclo[]>) => res.body)
      )
      .subscribe(
        (res: ISubciclo[]) => {
          this.subciclos = res;
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
    this.registerChangeInSubciclos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubciclo) {
    return item.id;
  }

  registerChangeInSubciclos() {
    this.eventSubscriber = this.eventManager.subscribe('subcicloListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
