import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  HorariolabComponent,
  HorariolabDetailComponent,
  HorariolabUpdateComponent,
  HorariolabDeletePopupComponent,
  HorariolabDeleteDialogComponent,
  horariolabRoute,
  horariolabPopupRoute
} from './';

const ENTITY_STATES = [...horariolabRoute, ...horariolabPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    HorariolabComponent,
    HorariolabDetailComponent,
    HorariolabUpdateComponent,
    HorariolabDeleteDialogComponent,
    HorariolabDeletePopupComponent
  ],
  entryComponents: [HorariolabComponent, HorariolabUpdateComponent, HorariolabDeleteDialogComponent, HorariolabDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieHorariolabModule {}
