import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Partida } from 'app/shared/model/partida.model';
import { PartidaService } from './partida.service';
import { PartidaComponent } from './partida.component';
import { PartidaDetailComponent } from './partida-detail.component';
import { PartidaUpdateComponent } from './partida-update.component';
import { PartidaDeletePopupComponent } from './partida-delete-dialog.component';
import { IPartida } from 'app/shared/model/partida.model';

@Injectable({ providedIn: 'root' })
export class PartidaResolve implements Resolve<IPartida> {
  constructor(private service: PartidaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPartida> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Partida>) => response.ok),
        map((partida: HttpResponse<Partida>) => partida.body)
      );
    }
    return of(new Partida());
  }
}

export const partidaRoute: Routes = [
  {
    path: '',
    component: PartidaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Partidas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PartidaDetailComponent,
    resolve: {
      partida: PartidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Partidas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PartidaUpdateComponent,
    resolve: {
      partida: PartidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Partidas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PartidaUpdateComponent,
    resolve: {
      partida: PartidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Partidas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const partidaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PartidaDeletePopupComponent,
    resolve: {
      partida: PartidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Partidas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
