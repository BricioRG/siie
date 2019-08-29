import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tipoperiodo } from 'app/shared/model/tipoperiodo.model';
import { TipoperiodoService } from './tipoperiodo.service';
import { TipoperiodoComponent } from './tipoperiodo.component';
import { TipoperiodoDetailComponent } from './tipoperiodo-detail.component';
import { TipoperiodoUpdateComponent } from './tipoperiodo-update.component';
import { TipoperiodoDeletePopupComponent } from './tipoperiodo-delete-dialog.component';
import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';

@Injectable({ providedIn: 'root' })
export class TipoperiodoResolve implements Resolve<ITipoperiodo> {
  constructor(private service: TipoperiodoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoperiodo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tipoperiodo>) => response.ok),
        map((tipoperiodo: HttpResponse<Tipoperiodo>) => tipoperiodo.body)
      );
    }
    return of(new Tipoperiodo());
  }
}

export const tipoperiodoRoute: Routes = [
  {
    path: '',
    component: TipoperiodoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipoperiodos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoperiodoDetailComponent,
    resolve: {
      tipoperiodo: TipoperiodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipoperiodos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoperiodoUpdateComponent,
    resolve: {
      tipoperiodo: TipoperiodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipoperiodos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoperiodoUpdateComponent,
    resolve: {
      tipoperiodo: TipoperiodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipoperiodos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoperiodoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoperiodoDeletePopupComponent,
    resolve: {
      tipoperiodo: TipoperiodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipoperiodos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
