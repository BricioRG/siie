import { NgModule } from '@angular/core';

import { SiieSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [SiieSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [SiieSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SiieSharedCommonModule {}
