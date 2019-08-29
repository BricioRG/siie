import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  FinanciamientoComponent,
  FinanciamientoDetailComponent,
  FinanciamientoUpdateComponent,
  FinanciamientoDeletePopupComponent,
  FinanciamientoDeleteDialogComponent,
  financiamientoRoute,
  financiamientoPopupRoute
} from './';

const ENTITY_STATES = [...financiamientoRoute, ...financiamientoPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FinanciamientoComponent,
    FinanciamientoDetailComponent,
    FinanciamientoUpdateComponent,
    FinanciamientoDeleteDialogComponent,
    FinanciamientoDeletePopupComponent
  ],
  entryComponents: [
    FinanciamientoComponent,
    FinanciamientoUpdateComponent,
    FinanciamientoDeleteDialogComponent,
    FinanciamientoDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieFinanciamientoModule {}
