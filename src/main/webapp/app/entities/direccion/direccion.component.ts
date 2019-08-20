import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDireccion } from 'app/shared/model/direccion.model';
import { AccountService } from 'app/core';
import { DireccionService } from './direccion.service';

@Component({
  selector: 'jhi-direccion',
  templateUrl: './direccion.component.html'
})
export class DireccionComponent implements OnInit, OnDestroy {
  direccions: IDireccion[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected direccionService: DireccionService,
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
      this.direccionService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IDireccion[]>) => res.ok),
          map((res: HttpResponse<IDireccion[]>) => res.body)
        )
        .subscribe((res: IDireccion[]) => (this.direccions = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.direccionService
      .query()
      .pipe(
        filter((res: HttpResponse<IDireccion[]>) => res.ok),
        map((res: HttpResponse<IDireccion[]>) => res.body)
      )
      .subscribe(
        (res: IDireccion[]) => {
          this.direccions = res;
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
    this.registerChangeInDireccions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDireccion) {
    return item.id;
  }

  registerChangeInDireccions() {
    this.eventSubscriber = this.eventManager.subscribe('direccionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
