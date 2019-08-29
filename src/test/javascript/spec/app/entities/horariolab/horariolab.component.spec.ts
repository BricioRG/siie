/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { HorariolabComponent } from 'app/entities/horariolab/horariolab.component';
import { HorariolabService } from 'app/entities/horariolab/horariolab.service';
import { Horariolab } from 'app/shared/model/horariolab.model';

describe('Component Tests', () => {
  describe('Horariolab Management Component', () => {
    let comp: HorariolabComponent;
    let fixture: ComponentFixture<HorariolabComponent>;
    let service: HorariolabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [HorariolabComponent],
        providers: []
      })
        .overrideTemplate(HorariolabComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HorariolabComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorariolabService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Horariolab(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.horariolabs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
