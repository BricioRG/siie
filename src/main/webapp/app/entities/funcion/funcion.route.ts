import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Funcion } from 'app/shared/model/funcion.model';
import { FuncionService } from './funcion.service';
import { FuncionComponent } from './funcion.component';
import { FuncionDetailComponent } from './funcion-detail.component';
import { FuncionUpdateComponent } from './funcion-update.component';
import { FuncionDeletePopupComponent } from './funcion-delete-dialog.component';
import { IFuncion } from 'app/shared/model/funcion.model';

@Injectable({ providedIn: 'root' })
export class FuncionResolve implements Resolve<IFuncion> {
  constructor(private service: FuncionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuncion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Funcion>) => response.ok),
        map((funcion: HttpResponse<Funcion>) => funcion.body)
      );
    }
    return of(new Funcion());
  }
}

export const funcionRoute: Routes = [
  {
    path: '',
    component: FuncionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuncionDetailComponent,
    resolve: {
      funcion: FuncionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuncionUpdateComponent,
    resolve: {
      funcion: FuncionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuncionUpdateComponent,
    resolve: {
      funcion: FuncionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const funcionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuncionDeletePopupComponent,
    resolve: {
      funcion: FuncionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
