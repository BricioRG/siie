import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Motivomov } from 'app/shared/model/motivomov.model';
import { MotivomovService } from './motivomov.service';
import { MotivomovComponent } from './motivomov.component';
import { MotivomovDetailComponent } from './motivomov-detail.component';
import { MotivomovUpdateComponent } from './motivomov-update.component';
import { MotivomovDeletePopupComponent } from './motivomov-delete-dialog.component';
import { IMotivomov } from 'app/shared/model/motivomov.model';

@Injectable({ providedIn: 'root' })
export class MotivomovResolve implements Resolve<IMotivomov> {
  constructor(private service: MotivomovService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMotivomov> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Motivomov>) => response.ok),
        map((motivomov: HttpResponse<Motivomov>) => motivomov.body)
      );
    }
    return of(new Motivomov());
  }
}

export const motivomovRoute: Routes = [
  {
    path: '',
    component: MotivomovComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Motivomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MotivomovDetailComponent,
    resolve: {
      motivomov: MotivomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Motivomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MotivomovUpdateComponent,
    resolve: {
      motivomov: MotivomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Motivomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MotivomovUpdateComponent,
    resolve: {
      motivomov: MotivomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Motivomovs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const motivomovPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MotivomovDeletePopupComponent,
    resolve: {
      motivomov: MotivomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Motivomovs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
