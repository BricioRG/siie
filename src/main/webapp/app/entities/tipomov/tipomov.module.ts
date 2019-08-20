import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  TipomovComponent,
  TipomovDetailComponent,
  TipomovUpdateComponent,
  TipomovDeletePopupComponent,
  TipomovDeleteDialogComponent,
  tipomovRoute,
  tipomovPopupRoute
} from './';

const ENTITY_STATES = [...tipomovRoute, ...tipomovPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipomovComponent,
    TipomovDetailComponent,
    TipomovUpdateComponent,
    TipomovDeleteDialogComponent,
    TipomovDeletePopupComponent
  ],
  entryComponents: [TipomovComponent, TipomovUpdateComponent, TipomovDeleteDialogComponent, TipomovDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieTipomovModule {}
