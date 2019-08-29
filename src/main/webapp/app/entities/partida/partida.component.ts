import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartida } from 'app/shared/model/partida.model';
import { AccountService } from 'app/core';
import { PartidaService } from './partida.service';

@Component({
  selector: 'jhi-partida',
  templateUrl: './partida.component.html'
})
export class PartidaComponent implements OnInit, OnDestroy {
  partidas: IPartida[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected partidaService: PartidaService,
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
      this.partidaService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPartida[]>) => res.ok),
          map((res: HttpResponse<IPartida[]>) => res.body)
        )
        .subscribe((res: IPartida[]) => (this.partidas = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.partidaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPartida[]>) => res.ok),
        map((res: HttpResponse<IPartida[]>) => res.body)
      )
      .subscribe(
        (res: IPartida[]) => {
          this.partidas = res;
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
    this.registerChangeInPartidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPartida) {
    return item.id;
  }

  registerChangeInPartidas() {
    this.eventSubscriber = this.eventManager.subscribe('partidaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
