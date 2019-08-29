import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';
import { AccountService } from 'app/core';
import { FuncionlaboralService } from './funcionlaboral.service';

@Component({
  selector: 'jhi-funcionlaboral',
  templateUrl: './funcionlaboral.component.html'
})
export class FuncionlaboralComponent implements OnInit, OnDestroy {
  funcionlaborals: IFuncionlaboral[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected funcionlaboralService: FuncionlaboralService,
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
      this.funcionlaboralService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IFuncionlaboral[]>) => res.ok),
          map((res: HttpResponse<IFuncionlaboral[]>) => res.body)
        )
        .subscribe((res: IFuncionlaboral[]) => (this.funcionlaborals = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.funcionlaboralService
      .query()
      .pipe(
        filter((res: HttpResponse<IFuncionlaboral[]>) => res.ok),
        map((res: HttpResponse<IFuncionlaboral[]>) => res.body)
      )
      .subscribe(
        (res: IFuncionlaboral[]) => {
          this.funcionlaborals = res;
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
    this.registerChangeInFuncionlaborals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFuncionlaboral) {
    return item.id;
  }

  registerChangeInFuncionlaborals() {
    this.eventSubscriber = this.eventManager.subscribe('funcionlaboralListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
