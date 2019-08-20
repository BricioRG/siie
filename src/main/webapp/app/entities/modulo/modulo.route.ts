import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Modulo } from 'app/shared/model/modulo.model';
import { ModuloService } from './modulo.service';
import { ModuloComponent } from './modulo.component';
import { ModuloDetailComponent } from './modulo-detail.component';
import { ModuloUpdateComponent } from './modulo-update.component';
import { ModuloDeletePopupComponent } from './modulo-delete-dialog.component';
import { IModulo } from 'app/shared/model/modulo.model';

@Injectable({ providedIn: 'root' })
export class ModuloResolve implements Resolve<IModulo> {
  constructor(private service: ModuloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModulo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Modulo>) => response.ok),
        map((modulo: HttpResponse<Modulo>) => modulo.body)
      );
    }
    return of(new Modulo());
  }
}

export const moduloRoute: Routes = [
  {
    path: '',
    component: ModuloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModuloDetailComponent,
    resolve: {
      modulo: ModuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModuloUpdateComponent,
    resolve: {
      modulo: ModuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModuloUpdateComponent,
    resolve: {
      modulo: ModuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modulos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const moduloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ModuloDeletePopupComponent,
    resolve: {
      modulo: ModuloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modulos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
