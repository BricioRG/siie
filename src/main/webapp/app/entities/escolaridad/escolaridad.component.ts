import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEscolaridad } from 'app/shared/model/escolaridad.model';
import { AccountService } from 'app/core';
import { EscolaridadService } from './escolaridad.service';

@Component({
  selector: 'jhi-escolaridad',
  templateUrl: './escolaridad.component.html'
})
export class EscolaridadComponent implements OnInit, OnDestroy {
  escolaridads: IEscolaridad[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected escolaridadService: EscolaridadService,
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
      this.escolaridadService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IEscolaridad[]>) => res.ok),
          map((res: HttpResponse<IEscolaridad[]>) => res.body)
        )
        .subscribe((res: IEscolaridad[]) => (this.escolaridads = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.escolaridadService
      .query()
      .pipe(
        filter((res: HttpResponse<IEscolaridad[]>) => res.ok),
        map((res: HttpResponse<IEscolaridad[]>) => res.body)
      )
      .subscribe(
        (res: IEscolaridad[]) => {
          this.escolaridads = res;
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
    this.registerChangeInEscolaridads();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEscolaridad) {
    return item.id;
  }

  registerChangeInEscolaridads() {
    this.eventSubscriber = this.eventManager.subscribe('escolaridadListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
