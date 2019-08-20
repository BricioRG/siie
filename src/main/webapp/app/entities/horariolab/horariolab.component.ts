import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHorariolab } from 'app/shared/model/horariolab.model';
import { AccountService } from 'app/core';
import { HorariolabService } from './horariolab.service';

@Component({
  selector: 'jhi-horariolab',
  templateUrl: './horariolab.component.html'
})
export class HorariolabComponent implements OnInit, OnDestroy {
  horariolabs: IHorariolab[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected horariolabService: HorariolabService,
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
      this.horariolabService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IHorariolab[]>) => res.ok),
          map((res: HttpResponse<IHorariolab[]>) => res.body)
        )
        .subscribe((res: IHorariolab[]) => (this.horariolabs = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.horariolabService
      .query()
      .pipe(
        filter((res: HttpResponse<IHorariolab[]>) => res.ok),
        map((res: HttpResponse<IHorariolab[]>) => res.body)
      )
      .subscribe(
        (res: IHorariolab[]) => {
          this.horariolabs = res;
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
    this.registerChangeInHorariolabs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHorariolab) {
    return item.id;
  }

  registerChangeInHorariolabs() {
    this.eventSubscriber = this.eventManager.subscribe('horariolabListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}