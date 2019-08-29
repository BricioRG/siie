import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Permiso } from 'app/shared/model/permiso.model';
import { PermisoService } from './permiso.service';
import { PermisoComponent } from './permiso.component';
import { PermisoDetailComponent } from './permiso-detail.component';
import { PermisoUpdateComponent } from './permiso-update.component';
import { PermisoDeletePopupComponent } from './permiso-delete-dialog.component';
import { IPermiso } from 'app/shared/model/permiso.model';

@Injectable({ providedIn: 'root' })
export class PermisoResolve implements Resolve<IPermiso> {
  constructor(private service: PermisoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPermiso> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Permiso>) => response.ok),
        map((permiso: HttpResponse<Permiso>) => permiso.body)
      );
    }
    return of(new Permiso());
  }
}

export const permisoRoute: Routes = [
  {
    path: '',
    component: PermisoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Permisos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PermisoDetailComponent,
    resolve: {
      permiso: PermisoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Permisos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PermisoUpdateComponent,
    resolve: {
      permiso: PermisoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Permisos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PermisoUpdateComponent,
    resolve: {
      permiso: PermisoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Permisos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const permisoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PermisoDeletePopupComponent,
    resolve: {
      permiso: PermisoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Permisos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
