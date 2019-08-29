import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Escuela } from 'app/shared/model/escuela.model';
import { EscuelaService } from './escuela.service';
import { EscuelaComponent } from './escuela.component';
import { EscuelaDetailComponent } from './escuela-detail.component';
import { EscuelaUpdateComponent } from './escuela-update.component';
import { EscuelaDeletePopupComponent } from './escuela-delete-dialog.component';
import { IEscuela } from 'app/shared/model/escuela.model';

@Injectable({ providedIn: 'root' })
export class EscuelaResolve implements Resolve<IEscuela> {
  constructor(private service: EscuelaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEscuela> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Escuela>) => response.ok),
        map((escuela: HttpResponse<Escuela>) => escuela.body)
      );
    }
    return of(new Escuela());
  }
}

export const escuelaRoute: Routes = [
  {
    path: '',
    component: EscuelaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escuelas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EscuelaDetailComponent,
    resolve: {
      escuela: EscuelaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escuelas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EscuelaUpdateComponent,
    resolve: {
      escuela: EscuelaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escuelas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EscuelaUpdateComponent,
    resolve: {
      escuela: EscuelaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escuelas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const escuelaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EscuelaDeletePopupComponent,
    resolve: {
      escuela: EscuelaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Escuelas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
