/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { PlazaComponent } from 'app/entities/plaza/plaza.component';
import { PlazaService } from 'app/entities/plaza/plaza.service';
import { Plaza } from 'app/shared/model/plaza.model';

describe('Component Tests', () => {
  describe('Plaza Management Component', () => {
    let comp: PlazaComponent;
    let fixture: ComponentFixture<PlazaComponent>;
    let service: PlazaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PlazaComponent],
        providers: []
      })
        .overrideTemplate(PlazaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlazaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlazaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Plaza(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plazas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
