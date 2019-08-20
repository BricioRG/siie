import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  EscolaridadComponent,
  EscolaridadDetailComponent,
  EscolaridadUpdateComponent,
  EscolaridadDeletePopupComponent,
  EscolaridadDeleteDialogComponent,
  escolaridadRoute,
  escolaridadPopupRoute
} from './';

const ENTITY_STATES = [...escolaridadRoute, ...escolaridadPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EscolaridadComponent,
    EscolaridadDetailComponent,
    EscolaridadUpdateComponent,
    EscolaridadDeleteDialogComponent,
    EscolaridadDeletePopupComponent
  ],
  entryComponents: [EscolaridadComponent, EscolaridadUpdateComponent, EscolaridadDeleteDialogComponent, EscolaridadDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieEscolaridadModule {}
