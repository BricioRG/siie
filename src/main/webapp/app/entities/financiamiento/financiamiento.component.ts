import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFinanciamiento } from 'app/shared/model/financiamiento.model';
import { AccountService } from 'app/core';
import { FinanciamientoService } from './financiamiento.service';

@Component({
  selector: 'jhi-financiamiento',
  templateUrl: './financiamiento.component.html'
})
export class FinanciamientoComponent implements OnInit, OnDestroy {
  financiamientos: IFinanciamiento[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected financiamientoService: FinanciamientoService,
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
      this.financiamientoService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IFinanciamiento[]>) => res.ok),
          map((res: HttpResponse<IFinanciamiento[]>) => res.body)
        )
        .subscribe((res: IFinanciamiento[]) => (this.financiamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.financiamientoService
      .query()
      .pipe(
        filter((res: HttpResponse<IFinanciamiento[]>) => res.ok),
        map((res: HttpResponse<IFinanciamiento[]>) => res.body)
      )
      .subscribe(
        (res: IFinanciamiento[]) => {
          this.financiamientos = res;
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
    this.registerChangeInFinanciamientos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFinanciamiento) {
    return item.id;
  }

  registerChangeInFinanciamientos() {
    this.eventSubscriber = this.eventManager.subscribe('financiamientoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
