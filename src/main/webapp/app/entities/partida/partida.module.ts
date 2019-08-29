import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  PartidaComponent,
  PartidaDetailComponent,
  PartidaUpdateComponent,
  PartidaDeletePopupComponent,
  PartidaDeleteDialogComponent,
  partidaRoute,
  partidaPopupRoute
} from './';

const ENTITY_STATES = [...partidaRoute, ...partidaPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PartidaComponent,
    PartidaDetailComponent,
    PartidaUpdateComponent,
    PartidaDeleteDialogComponent,
    PartidaDeletePopupComponent
  ],
  entryComponents: [PartidaComponent, PartidaUpdateComponent, PartidaDeleteDialogComponent, PartidaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiiePartidaModule {}
