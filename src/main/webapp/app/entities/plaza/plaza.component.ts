import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlaza } from 'app/shared/model/plaza.model';
import { AccountService } from 'app/core';
import { PlazaService } from './plaza.service';

@Component({
  selector: 'jhi-plaza',
  templateUrl: './plaza.component.html'
})
export class PlazaComponent implements OnInit, OnDestroy {
  plazas: IPlaza[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected plazaService: PlazaService,
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
      this.plazaService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPlaza[]>) => res.ok),
          map((res: HttpResponse<IPlaza[]>) => res.body)
        )
        .subscribe((res: IPlaza[]) => (this.plazas = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.plazaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPlaza[]>) => res.ok),
        map((res: HttpResponse<IPlaza[]>) => res.body)
      )
      .subscribe(
        (res: IPlaza[]) => {
          this.plazas = res;
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
    this.registerChangeInPlazas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPlaza) {
    return item.id;
  }

  registerChangeInPlazas() {
    this.eventSubscriber = this.eventManager.subscribe('plazaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
