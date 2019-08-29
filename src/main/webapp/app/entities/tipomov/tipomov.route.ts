import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tipomov } from 'app/shared/model/tipomov.model';
import { TipomovService } from './tipomov.service';
import { TipomovComponent } from './tipomov.component';
import { TipomovDetailComponent } from './tipomov-detail.component';
import { TipomovUpdateComponent } from './tipomov-update.component';
import { TipomovDeletePopupComponent } from './tipomov-delete-dialog.component';
import { ITipomov } from 'app/shared/model/tipomov.model';

@Injectable({ providedIn: 'root' })
export class TipomovResolve implements Resolve<ITipomov> {
  constructor(private service: TipomovService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipomov> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tipomov>) => response.ok),
        map((tipomov: HttpResponse<Tipomov>) => tipomov.body)
      );
    }
    return of(new Tipomov());
  }
}

export const tipomovRoute: Routes = [
  {
    path: '',
    component: TipomovComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipomovDetailComponent,
    resolve: {
      tipomov: TipomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipomovUpdateComponent,
    resolve: {
      tipomov: TipomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipomovs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipomovUpdateComponent,
    resolve: {
      tipomov: TipomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipomovs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipomovPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipomovDeletePopupComponent,
    resolve: {
      tipomov: TipomovResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tipomovs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
