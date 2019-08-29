import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Jornada } from 'app/shared/model/jornada.model';
import { JornadaService } from './jornada.service';
import { JornadaComponent } from './jornada.component';
import { JornadaDetailComponent } from './jornada-detail.component';
import { JornadaUpdateComponent } from './jornada-update.component';
import { JornadaDeletePopupComponent } from './jornada-delete-dialog.component';
import { IJornada } from 'app/shared/model/jornada.model';

@Injectable({ providedIn: 'root' })
export class JornadaResolve implements Resolve<IJornada> {
  constructor(private service: JornadaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJornada> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Jornada>) => response.ok),
        map((jornada: HttpResponse<Jornada>) => jornada.body)
      );
    }
    return of(new Jornada());
  }
}

export const jornadaRoute: Routes = [
  {
    path: '',
    component: JornadaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Jornadas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JornadaDetailComponent,
    resolve: {
      jornada: JornadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Jornadas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JornadaUpdateComponent,
    resolve: {
      jornada: JornadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Jornadas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JornadaUpdateComponent,
    resolve: {
      jornada: JornadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Jornadas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const jornadaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: JornadaDeletePopupComponent,
    resolve: {
      jornada: JornadaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Jornadas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
