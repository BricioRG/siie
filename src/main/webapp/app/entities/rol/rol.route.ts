import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rol } from 'app/shared/model/rol.model';
import { RolService } from './rol.service';
import { RolComponent } from './rol.component';
import { RolDetailComponent } from './rol-detail.component';
import { RolUpdateComponent } from './rol-update.component';
import { RolDeletePopupComponent } from './rol-delete-dialog.component';
import { IRol } from 'app/shared/model/rol.model';

@Injectable({ providedIn: 'root' })
export class RolResolve implements Resolve<IRol> {
  constructor(private service: RolService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRol> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Rol>) => response.ok),
        map((rol: HttpResponse<Rol>) => rol.body)
      );
    }
    return of(new Rol());
  }
}

export const rolRoute: Routes = [
  {
    path: '',
    component: RolComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Rols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RolDetailComponent,
    resolve: {
      rol: RolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Rols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RolUpdateComponent,
    resolve: {
      rol: RolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Rols'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RolUpdateComponent,
    resolve: {
      rol: RolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Rols'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rolPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RolDeletePopupComponent,
    resolve: {
      rol: RolResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Rols'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
