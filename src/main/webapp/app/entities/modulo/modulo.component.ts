import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IModulo } from 'app/shared/model/modulo.model';
import { AccountService } from 'app/core';
import { ModuloService } from './modulo.service';

@Component({
  selector: 'jhi-modulo',
  templateUrl: './modulo.component.html'
})
export class ModuloComponent implements OnInit, OnDestroy {
  modulos: IModulo[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected moduloService: ModuloService,
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
      this.moduloService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IModulo[]>) => res.ok),
          map((res: HttpResponse<IModulo[]>) => res.body)
        )
        .subscribe((res: IModulo[]) => (this.modulos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.moduloService
      .query()
      .pipe(
        filter((res: HttpResponse<IModulo[]>) => res.ok),
        map((res: HttpResponse<IModulo[]>) => res.body)
      )
      .subscribe(
        (res: IModulo[]) => {
          this.modulos = res;
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
    this.registerChangeInModulos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IModulo) {
    return item.id;
  }

  registerChangeInModulos() {
    this.eventSubscriber = this.eventManager.subscribe('moduloListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
