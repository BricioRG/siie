import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  DireccionComponent,
  DireccionDetailComponent,
  DireccionUpdateComponent,
  DireccionDeletePopupComponent,
  DireccionDeleteDialogComponent,
  direccionRoute,
  direccionPopupRoute
} from './';

const ENTITY_STATES = [...direccionRoute, ...direccionPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DireccionComponent,
    DireccionDetailComponent,
    DireccionUpdateComponent,
    DireccionDeleteDialogComponent,
    DireccionDeletePopupComponent
  ],
  entryComponents: [DireccionComponent, DireccionUpdateComponent, DireccionDeleteDialogComponent, DireccionDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieDireccionModule {}
