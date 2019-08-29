import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Plaza } from 'app/shared/model/plaza.model';
import { PlazaService } from './plaza.service';
import { PlazaComponent } from './plaza.component';
import { PlazaDetailComponent } from './plaza-detail.component';
import { PlazaUpdateComponent } from './plaza-update.component';
import { PlazaDeletePopupComponent } from './plaza-delete-dialog.component';
import { IPlaza } from 'app/shared/model/plaza.model';

@Injectable({ providedIn: 'root' })
export class PlazaResolve implements Resolve<IPlaza> {
  constructor(private service: PlazaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlaza> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Plaza>) => response.ok),
        map((plaza: HttpResponse<Plaza>) => plaza.body)
      );
    }
    return of(new Plaza());
  }
}

export const plazaRoute: Routes = [
  {
    path: '',
    component: PlazaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Plazas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlazaDetailComponent,
    resolve: {
      plaza: PlazaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Plazas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlazaUpdateComponent,
    resolve: {
      plaza: PlazaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Plazas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlazaUpdateComponent,
    resolve: {
      plaza: PlazaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Plazas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const plazaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlazaDeletePopupComponent,
    resolve: {
      plaza: PlazaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Plazas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
