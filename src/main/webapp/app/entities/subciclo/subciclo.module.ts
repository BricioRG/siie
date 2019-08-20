import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  SubcicloComponent,
  SubcicloDetailComponent,
  SubcicloUpdateComponent,
  SubcicloDeletePopupComponent,
  SubcicloDeleteDialogComponent,
  subcicloRoute,
  subcicloPopupRoute
} from './';

const ENTITY_STATES = [...subcicloRoute, ...subcicloPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SubcicloComponent,
    SubcicloDetailComponent,
    SubcicloUpdateComponent,
    SubcicloDeleteDialogComponent,
    SubcicloDeletePopupComponent
  ],
  entryComponents: [SubcicloComponent, SubcicloUpdateComponent, SubcicloDeleteDialogComponent, SubcicloDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieSubcicloModule {}
