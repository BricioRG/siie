import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  RolComponent,
  RolDetailComponent,
  RolUpdateComponent,
  RolDeletePopupComponent,
  RolDeleteDialogComponent,
  rolRoute,
  rolPopupRoute
} from './';

const ENTITY_STATES = [...rolRoute, ...rolPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RolComponent, RolDetailComponent, RolUpdateComponent, RolDeleteDialogComponent, RolDeletePopupComponent],
  entryComponents: [RolComponent, RolUpdateComponent, RolDeleteDialogComponent, RolDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieRolModule {}
