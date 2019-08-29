import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';
import { AccountService } from 'app/core';
import { TipoperiodoService } from './tipoperiodo.service';

@Component({
  selector: 'jhi-tipoperiodo',
  templateUrl: './tipoperiodo.component.html'
})
export class TipoperiodoComponent implements OnInit, OnDestroy {
  tipoperiodos: ITipoperiodo[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected tipoperiodoService: TipoperiodoService,
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
      this.tipoperiodoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ITipoperiodo[]>) => res.ok),
          map((res: HttpResponse<ITipoperiodo[]>) => res.body)
        )
        .subscribe((res: ITipoperiodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.tipoperiodoService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipoperiodo[]>) => res.ok),
        map((res: HttpResponse<ITipoperiodo[]>) => res.body)
      )
      .subscribe(
        (res: ITipoperiodo[]) => {
          this.tipoperiodos = res;
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
    this.registerChangeInTipoperiodos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipoperiodo) {
    return item.id;
  }

  registerChangeInTipoperiodos() {
    this.eventSubscriber = this.eventManager.subscribe('tipoperiodoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
