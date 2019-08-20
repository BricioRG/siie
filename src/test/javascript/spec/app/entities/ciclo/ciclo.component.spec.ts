/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { CicloComponent } from 'app/entities/ciclo/ciclo.component';
import { CicloService } from 'app/entities/ciclo/ciclo.service';
import { Ciclo } from 'app/shared/model/ciclo.model';

describe('Component Tests', () => {
  describe('Ciclo Management Component', () => {
    let comp: CicloComponent;
    let fixture: ComponentFixture<CicloComponent>;
    let service: CicloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [CicloComponent],
        providers: []
      })
        .overrideTemplate(CicloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CicloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CicloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ciclo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ciclos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
