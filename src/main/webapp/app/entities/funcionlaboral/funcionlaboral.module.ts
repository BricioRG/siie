import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SiieSharedModule } from 'app/shared';
import {
  FuncionlaboralComponent,
  FuncionlaboralDetailComponent,
  FuncionlaboralUpdateComponent,
  FuncionlaboralDeletePopupComponent,
  FuncionlaboralDeleteDialogComponent,
  funcionlaboralRoute,
  funcionlaboralPopupRoute
} from './';

const ENTITY_STATES = [...funcionlaboralRoute, ...funcionlaboralPopupRoute];

@NgModule({
  imports: [SiieSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FuncionlaboralComponent,
    FuncionlaboralDetailComponent,
    FuncionlaboralUpdateComponent,
    FuncionlaboralDeleteDialogComponent,
    FuncionlaboralDeletePopupComponent
  ],
  entryComponents: [
    FuncionlaboralComponent,
    FuncionlaboralUpdateComponent,
    FuncionlaboralDeleteDialogComponent,
    FuncionlaboralDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieFuncionlaboralModule {}
