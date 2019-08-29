import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPermiso } from 'app/shared/model/permiso.model';
import { AccountService } from 'app/core';
import { PermisoService } from './permiso.service';

@Component({
  selector: 'jhi-permiso',
  templateUrl: './permiso.component.html'
})
export class PermisoComponent implements OnInit, OnDestroy {
  permisos: IPermiso[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected permisoService: PermisoService,
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
      this.permisoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPermiso[]>) => res.ok),
          map((res: HttpResponse<IPermiso[]>) => res.body)
        )
        .subscribe((res: IPermiso[]) => (this.permisos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.permisoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPermiso[]>) => res.ok),
        map((res: HttpResponse<IPermiso[]>) => res.body)
      )
      .subscribe(
        (res: IPermiso[]) => {
          this.permisos = res;
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
    this.registerChangeInPermisos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPermiso) {
    return item.id;
  }

  registerChangeInPermisos() {
    this.eventSubscriber = this.eventManager.subscribe('permisoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
