import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Financiamiento } from 'app/shared/model/financiamiento.model';
import { FinanciamientoService } from './financiamiento.service';
import { FinanciamientoComponent } from './financiamiento.component';
import { FinanciamientoDetailComponent } from './financiamiento-detail.component';
import { FinanciamientoUpdateComponent } from './financiamiento-update.component';
import { FinanciamientoDeletePopupComponent } from './financiamiento-delete-dialog.component';
import { IFinanciamiento } from 'app/shared/model/financiamiento.model';

@Injectable({ providedIn: 'root' })
export class FinanciamientoResolve implements Resolve<IFinanciamiento> {
  constructor(private service: FinanciamientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFinanciamiento> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Financiamiento>) => response.ok),
        map((financiamiento: HttpResponse<Financiamiento>) => financiamiento.body)
      );
    }
    return of(new Financiamiento());
  }
}

export const financiamientoRoute: Routes = [
  {
    path: '',
    component: FinanciamientoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Financiamientos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FinanciamientoDetailComponent,
    resolve: {
      financiamiento: FinanciamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Financiamientos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FinanciamientoUpdateComponent,
    resolve: {
      financiamiento: FinanciamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Financiamientos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FinanciamientoUpdateComponent,
    resolve: {
      financiamiento: FinanciamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Financiamientos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const financiamientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FinanciamientoDeletePopupComponent,
    resolve: {
      financiamiento: FinanciamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Financiamientos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
