import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Horariolab } from 'app/shared/model/horariolab.model';
import { HorariolabService } from './horariolab.service';
import { HorariolabComponent } from './horariolab.component';
import { HorariolabDetailComponent } from './horariolab-detail.component';
import { HorariolabUpdateComponent } from './horariolab-update.component';
import { HorariolabDeletePopupComponent } from './horariolab-delete-dialog.component';
import { IHorariolab } from 'app/shared/model/horariolab.model';

@Injectable({ providedIn: 'root' })
export class HorariolabResolve implements Resolve<IHorariolab> {
  constructor(private service: HorariolabService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHorariolab> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Horariolab>) => response.ok),
        map((horariolab: HttpResponse<Horariolab>) => horariolab.body)
      );
    }
    return of(new Horariolab());
  }
}

export const horariolabRoute: Routes = [
  {
    path: '',
    component: HorariolabComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Horariolabs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HorariolabDetailComponent,
    resolve: {
      horariolab: HorariolabResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Horariolabs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HorariolabUpdateComponent,
    resolve: {
      horariolab: HorariolabResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Horariolabs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HorariolabUpdateComponent,
    resolve: {
      horariolab: HorariolabResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Horariolabs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const horariolabPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HorariolabDeletePopupComponent,
    resolve: {
      horariolab: HorariolabResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Horariolabs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
