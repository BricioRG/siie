/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { JornadaComponent } from 'app/entities/jornada/jornada.component';
import { JornadaService } from 'app/entities/jornada/jornada.service';
import { Jornada } from 'app/shared/model/jornada.model';

describe('Component Tests', () => {
  describe('Jornada Management Component', () => {
    let comp: JornadaComponent;
    let fixture: ComponentFixture<JornadaComponent>;
    let service: JornadaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [JornadaComponent],
        providers: []
      })
        .overrideTemplate(JornadaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JornadaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JornadaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Jornada(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jornadas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
