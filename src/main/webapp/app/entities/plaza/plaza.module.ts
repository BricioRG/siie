import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  PlazaComponent,
  PlazaDetailComponent,
  PlazaUpdateComponent,
  PlazaDeletePopupComponent,
  PlazaDeleteDialogComponent,
  plazaRoute,
  plazaPopupRoute
} from './';

const ENTITY_STATES = [...plazaRoute, ...plazaPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PlazaComponent, PlazaDetailComponent, PlazaUpdateComponent, PlazaDeleteDialogComponent, PlazaDeletePopupComponent],
  entryComponents: [PlazaComponent, PlazaUpdateComponent, PlazaDeleteDialogComponent, PlazaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiiePlazaModule {}
