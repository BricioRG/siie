/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { EscuelaComponent } from 'app/entities/escuela/escuela.component';
import { EscuelaService } from 'app/entities/escuela/escuela.service';
import { Escuela } from 'app/shared/model/escuela.model';

describe('Component Tests', () => {
  describe('Escuela Management Component', () => {
    let comp: EscuelaComponent;
    let fixture: ComponentFixture<EscuelaComponent>;
    let service: EscuelaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscuelaComponent],
        providers: []
      })
        .overrideTemplate(EscuelaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscuelaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscuelaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Escuela(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.escuelas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
