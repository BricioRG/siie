import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  CicloComponent,
  CicloDetailComponent,
  CicloUpdateComponent,
  CicloDeletePopupComponent,
  CicloDeleteDialogComponent,
  cicloRoute,
  cicloPopupRoute
} from './';

const ENTITY_STATES = [...cicloRoute, ...cicloPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CicloComponent, CicloDetailComponent, CicloUpdateComponent, CicloDeleteDialogComponent, CicloDeletePopupComponent],
  entryComponents: [CicloComponent, CicloUpdateComponent, CicloDeleteDialogComponent, CicloDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieCicloModule {}
