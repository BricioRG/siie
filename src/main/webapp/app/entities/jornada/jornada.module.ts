import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  JornadaComponent,
  JornadaDetailComponent,
  JornadaUpdateComponent,
  JornadaDeletePopupComponent,
  JornadaDeleteDialogComponent,
  jornadaRoute,
  jornadaPopupRoute
} from './';

const ENTITY_STATES = [...jornadaRoute, ...jornadaPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    JornadaComponent,
    JornadaDetailComponent,
    JornadaUpdateComponent,
    JornadaDeleteDialogComponent,
    JornadaDeletePopupComponent
  ],
  entryComponents: [JornadaComponent, JornadaUpdateComponent, JornadaDeleteDialogComponent, JornadaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieJornadaModule {}
