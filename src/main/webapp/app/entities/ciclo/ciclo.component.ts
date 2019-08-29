import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICiclo } from 'app/shared/model/ciclo.model';
import { AccountService } from 'app/core';
import { CicloService } from './ciclo.service';

@Component({
  selector: 'jhi-ciclo',
  templateUrl: './ciclo.component.html'
})
export class CicloComponent implements OnInit, OnDestroy {
  ciclos: ICiclo[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected cicloService: CicloService,
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
      this.cicloService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<ICiclo[]>) => res.ok),
          map((res: HttpResponse<ICiclo[]>) => res.body)
        )
        .subscribe((res: ICiclo[]) => (this.ciclos = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.cicloService
      .query()
      .pipe(
        filter((res: HttpResponse<ICiclo[]>) => res.ok),
        map((res: HttpResponse<ICiclo[]>) => res.body)
      )
      .subscribe(
        (res: ICiclo[]) => {
          this.ciclos = res;
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
    this.registerChangeInCiclos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICiclo) {
    return item.id;
  }

  registerChangeInCiclos() {
    this.eventSubscriber = this.eventManager.subscribe('cicloListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
