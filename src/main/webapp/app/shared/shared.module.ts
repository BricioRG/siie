import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SiieSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SiieSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SiieSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieSharedModule {
  static forRoot() {
    return {
      ngModule: SiieSharedModule
    };
  }
}
