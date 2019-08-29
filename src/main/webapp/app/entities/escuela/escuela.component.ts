import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEscuela } from 'app/shared/model/escuela.model';
import { AccountService } from 'app/core';
import { EscuelaService } from './escuela.service';

@Component({
  selector: 'jhi-escuela',
  templateUrl: './escuela.component.html'
})
export class EscuelaComponent implements OnInit, OnDestroy {
  escuelas: IEscuela[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected escuelaService: EscuelaService,
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
      this.escuelaService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IEscuela[]>) => res.ok),
          map((res: HttpResponse<IEscuela[]>) => res.body)
        )
        .subscribe((res: IEscuela[]) => (this.escuelas = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.escuelaService
      .query()
      .pipe(
        filter((res: HttpResponse<IEscuela[]>) => res.ok),
        map((res: HttpResponse<IEscuela[]>) => res.body)
      )
      .subscribe(
        (res: IEscuela[]) => {
          this.escuelas = res;
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
    this.registerChangeInEscuelas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEscuela) {
    return item.id;
  }

  registerChangeInEscuelas() {
    this.eventSubscriber = this.eventManager.subscribe('escuelaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
