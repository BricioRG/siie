import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  EscuelaComponent,
  EscuelaDetailComponent,
  EscuelaUpdateComponent,
  EscuelaDeletePopupComponent,
  EscuelaDeleteDialogComponent,
  escuelaRoute,
  escuelaPopupRoute
} from './';

const ENTITY_STATES = [...escuelaRoute, ...escuelaPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EscuelaComponent,
    EscuelaDetailComponent,
    EscuelaUpdateComponent,
    EscuelaDeleteDialogComponent,
    EscuelaDeletePopupComponent
  ],
  entryComponents: [EscuelaComponent, EscuelaUpdateComponent, EscuelaDeleteDialogComponent, EscuelaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieEscuelaModule {}
