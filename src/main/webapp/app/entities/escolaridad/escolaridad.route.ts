import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Escolaridad } from 'app/shared/model/escolaridad.model';
import { EscolaridadService } from './escolaridad.service';
import { EscolaridadComponent } from './escolaridad.component';
import { EscolaridadDetailComponent } from './escolaridad-detail.component';
import { EscolaridadUpdateComponent } from './escolaridad-update.component';
import { EscolaridadDeletePopupComponent } from './escolaridad-delete-dialog.component';
import { IEscolaridad } from 'app/shared/model/escolaridad.model';

@Injectable({ providedIn: 'root' })
export class EscolaridadResolve implements Resolve<IEscolaridad> {
  constructor(private service: EscolaridadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEscolaridad> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Escolaridad>) => response.ok),
        map((escolaridad: HttpResponse<Escolaridad>) => escolaridad.body)
      );
    }
    return of(new Escolaridad());
  }
}

export const escolaridadRoute: Routes = [
  {
    path: '',
    component: EscolaridadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escolaridads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EscolaridadDetailComponent,
    resolve: {
      escolaridad: EscolaridadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escolaridads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EscolaridadUpdateComponent,
    resolve: {
      escolaridad: EscolaridadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escolaridads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EscolaridadUpdateComponent,
    resolve: {
      escolaridad: EscolaridadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escolaridads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const escolaridadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EscolaridadDeletePopupComponent,
    resolve: {
      escolaridad: EscolaridadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escolaridads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
