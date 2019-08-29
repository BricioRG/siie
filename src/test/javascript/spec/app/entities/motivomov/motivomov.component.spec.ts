/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { MotivomovComponent } from 'app/entities/motivomov/motivomov.component';
import { MotivomovService } from 'app/entities/motivomov/motivomov.service';
import { Motivomov } from 'app/shared/model/motivomov.model';

describe('Component Tests', () => {
  describe('Motivomov Management Component', () => {
    let comp: MotivomovComponent;
    let fixture: ComponentFixture<MotivomovComponent>;
    let service: MotivomovService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [MotivomovComponent],
        providers: []
      })
        .overrideTemplate(MotivomovComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotivomovComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotivomovService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Motivomov(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.motivomovs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
