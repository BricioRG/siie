/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { FinanciamientoComponent } from 'app/entities/financiamiento/financiamiento.component';
import { FinanciamientoService } from 'app/entities/financiamiento/financiamiento.service';
import { Financiamiento } from 'app/shared/model/financiamiento.model';

describe('Component Tests', () => {
  describe('Financiamiento Management Component', () => {
    let comp: FinanciamientoComponent;
    let fixture: ComponentFixture<FinanciamientoComponent>;
    let service: FinanciamientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FinanciamientoComponent],
        providers: []
      })
        .overrideTemplate(FinanciamientoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinanciamientoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanciamientoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Financiamiento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.financiamientos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
