import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  MotivomovComponent,
  MotivomovDetailComponent,
  MotivomovUpdateComponent,
  MotivomovDeletePopupComponent,
  MotivomovDeleteDialogComponent,
  motivomovRoute,
  motivomovPopupRoute
} from './';

const ENTITY_STATES = [...motivomovRoute, ...motivomovPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MotivomovComponent,
    MotivomovDetailComponent,
    MotivomovUpdateComponent,
    MotivomovDeleteDialogComponent,
    MotivomovDeletePopupComponent
  ],
  entryComponents: [MotivomovComponent, MotivomovUpdateComponent, MotivomovDeleteDialogComponent, MotivomovDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieMotivomovModule {}
