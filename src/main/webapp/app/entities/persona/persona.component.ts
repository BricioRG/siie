import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersona } from 'app/shared/model/persona.model';
import { AccountService } from 'app/core';
import { PersonaService } from './persona.service';

@Component({
  selector: 'jhi-persona',
  templateUrl: './persona.component.html'
})
export class PersonaComponent implements OnInit, OnDestroy {
  personas: IPersona[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected personaService: PersonaService,
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
      this.personaService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IPersona[]>) => res.ok),
          map((res: HttpResponse<IPersona[]>) => res.body)
        )
        .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.personaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersona[]>) => res.ok),
        map((res: HttpResponse<IPersona[]>) => res.body)
      )
      .subscribe(
        (res: IPersona[]) => {
          this.personas = res;
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
    this.registerChangeInPersonas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersona) {
    return item.id;
  }

  registerChangeInPersonas() {
    this.eventSubscriber = this.eventManager.subscribe('personaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
