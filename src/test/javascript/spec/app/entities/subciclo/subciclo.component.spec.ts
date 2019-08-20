/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { SubcicloComponent } from 'app/entities/subciclo/subciclo.component';
import { SubcicloService } from 'app/entities/subciclo/subciclo.service';
import { Subciclo } from 'app/shared/model/subciclo.model';

describe('Component Tests', () => {
  describe('Subciclo Management Component', () => {
    let comp: SubcicloComponent;
    let fixture: ComponentFixture<SubcicloComponent>;
    let service: SubcicloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [SubcicloComponent],
        providers: []
      })
        .overrideTemplate(SubcicloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcicloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcicloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Subciclo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subciclos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
