import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  TipoperiodoComponent,
  TipoperiodoDetailComponent,
  TipoperiodoUpdateComponent,
  TipoperiodoDeletePopupComponent,
  TipoperiodoDeleteDialogComponent,
  tipoperiodoRoute,
  tipoperiodoPopupRoute
} from './';

const ENTITY_STATES = [...tipoperiodoRoute, ...tipoperiodoPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoperiodoComponent,
    TipoperiodoDetailComponent,
    TipoperiodoUpdateComponent,
    TipoperiodoDeleteDialogComponent,
    TipoperiodoDeletePopupComponent
  ],
  entryComponents: [TipoperiodoComponent, TipoperiodoUpdateComponent, TipoperiodoDeleteDialogComponent, TipoperiodoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieTipoperiodoModule {}
