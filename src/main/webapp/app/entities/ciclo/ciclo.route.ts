import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ciclo } from 'app/shared/model/ciclo.model';
import { CicloService } from './ciclo.service';
import { CicloComponent } from './ciclo.component';
import { CicloDetailComponent } from './ciclo-detail.component';
import { CicloUpdateComponent } from './ciclo-update.component';
import { CicloDeletePopupComponent } from './ciclo-delete-dialog.component';
import { ICiclo } from 'app/shared/model/ciclo.model';

@Injectable({ providedIn: 'root' })
export class CicloResolve implements Resolve<ICiclo> {
  constructor(private service: CicloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICiclo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ciclo>) => response.ok),
        map((ciclo: HttpResponse<Ciclo>) => ciclo.body)
      );
    }
    return of(new Ciclo());
  }
}

export const cicloRoute: Routes = [
  {
    path: '',
    component: CicloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CicloDetailComponent,
    resolve: {
      ciclo: CicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CicloUpdateComponent,
    resolve: {
      ciclo: CicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CicloUpdateComponent,
    resolve: {
      ciclo: CicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ciclos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cicloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CicloDeletePopupComponent,
    resolve: {
      ciclo: CicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Ciclos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
