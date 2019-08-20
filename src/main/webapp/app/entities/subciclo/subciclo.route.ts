import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Subciclo } from 'app/shared/model/subciclo.model';
import { SubcicloService } from './subciclo.service';
import { SubcicloComponent } from './subciclo.component';
import { SubcicloDetailComponent } from './subciclo-detail.component';
import { SubcicloUpdateComponent } from './subciclo-update.component';
import { SubcicloDeletePopupComponent } from './subciclo-delete-dialog.component';
import { ISubciclo } from 'app/shared/model/subciclo.model';

@Injectable({ providedIn: 'root' })
export class SubcicloResolve implements Resolve<ISubciclo> {
  constructor(private service: SubcicloService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubciclo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Subciclo>) => response.ok),
        map((subciclo: HttpResponse<Subciclo>) => subciclo.body)
      );
    }
    return of(new Subciclo());
  }
}

export const subcicloRoute: Routes = [
  {
    path: '',
    component: SubcicloComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubcicloDetailComponent,
    resolve: {
      subciclo: SubcicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubcicloUpdateComponent,
    resolve: {
      subciclo: SubcicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subciclos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubcicloUpdateComponent,
    resolve: {
      subciclo: SubcicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subciclos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subcicloPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubcicloDeletePopupComponent,
    resolve: {
      subciclo: SubcicloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Subciclos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
