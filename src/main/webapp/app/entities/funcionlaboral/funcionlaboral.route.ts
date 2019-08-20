import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Funcionlaboral } from 'app/shared/model/funcionlaboral.model';
import { FuncionlaboralService } from './funcionlaboral.service';
import { FuncionlaboralComponent } from './funcionlaboral.component';
import { FuncionlaboralDetailComponent } from './funcionlaboral-detail.component';
import { FuncionlaboralUpdateComponent } from './funcionlaboral-update.component';
import { FuncionlaboralDeletePopupComponent } from './funcionlaboral-delete-dialog.component';
import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';

@Injectable({ providedIn: 'root' })
export class FuncionlaboralResolve implements Resolve<IFuncionlaboral> {
  constructor(private service: FuncionlaboralService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuncionlaboral> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Funcionlaboral>) => response.ok),
        map((funcionlaboral: HttpResponse<Funcionlaboral>) => funcionlaboral.body)
      );
    }
    return of(new Funcionlaboral());
  }
}

export const funcionlaboralRoute: Routes = [
  {
    path: '',
    component: FuncionlaboralComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcionlaborals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuncionlaboralDetailComponent,
    resolve: {
      funcionlaboral: FuncionlaboralResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcionlaborals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuncionlaboralUpdateComponent,
    resolve: {
      funcionlaboral: FuncionlaboralResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcionlaborals'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuncionlaboralUpdateComponent,
    resolve: {
      funcionlaboral: FuncionlaboralResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcionlaborals'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const funcionlaboralPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuncionlaboralDeletePopupComponent,
    resolve: {
      funcionlaboral: FuncionlaboralResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Funcionlaborals'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
