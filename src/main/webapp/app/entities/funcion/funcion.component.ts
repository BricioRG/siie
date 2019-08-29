import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFuncion } from 'app/shared/model/funcion.model';
import { AccountService } from 'app/core';
import { FuncionService } from './funcion.service';

@Component({
  selector: 'jhi-funcion',
  templateUrl: './funcion.component.html'
})
export class FuncionComponent implements OnInit, OnDestroy {
  funcions: IFuncion[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected funcionService: FuncionService,
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
      this.funcionService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IFuncion[]>) => res.ok),
          map((res: HttpResponse<IFuncion[]>) => res.body)
        )
        .subscribe((res: IFuncion[]) => (this.funcions = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.funcionService
      .query()
      .pipe(
        filter((res: HttpResponse<IFuncion[]>) => res.ok),
        map((res: HttpResponse<IFuncion[]>) => res.body)
      )
      .subscribe(
        (res: IFuncion[]) => {
          this.funcions = res;
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
    this.registerChangeInFuncions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFuncion) {
    return item.id;
  }

  registerChangeInFuncions() {
    this.eventSubscriber = this.eventManager.subscribe('funcionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
