import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJornada } from 'app/shared/model/jornada.model';
import { AccountService } from 'app/core';
import { JornadaService } from './jornada.service';

@Component({
  selector: 'jhi-jornada',
  templateUrl: './jornada.component.html'
})
export class JornadaComponent implements OnInit, OnDestroy {
  jornadas: IJornada[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected jornadaService: JornadaService,
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
      this.jornadaService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IJornada[]>) => res.ok),
          map((res: HttpResponse<IJornada[]>) => res.body)
        )
        .subscribe((res: IJornada[]) => (this.jornadas = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.jornadaService
      .query()
      .pipe(
        filter((res: HttpResponse<IJornada[]>) => res.ok),
        map((res: HttpResponse<IJornada[]>) => res.body)
      )
      .subscribe(
        (res: IJornada[]) => {
          this.jornadas = res;
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
    this.registerChangeInJornadas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IJornada) {
    return item.id;
  }

  registerChangeInJornadas() {
    this.eventSubscriber = this.eventManager.subscribe('jornadaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
