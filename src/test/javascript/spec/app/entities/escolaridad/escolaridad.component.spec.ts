/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { EscolaridadComponent } from 'app/entities/escolaridad/escolaridad.component';
import { EscolaridadService } from 'app/entities/escolaridad/escolaridad.service';
import { Escolaridad } from 'app/shared/model/escolaridad.model';

describe('Component Tests', () => {
  describe('Escolaridad Management Component', () => {
    let comp: EscolaridadComponent;
    let fixture: ComponentFixture<EscolaridadComponent>;
    let service: EscolaridadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscolaridadComponent],
        providers: []
      })
        .overrideTemplate(EscolaridadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscolaridadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscolaridadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Escolaridad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.escolaridads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
