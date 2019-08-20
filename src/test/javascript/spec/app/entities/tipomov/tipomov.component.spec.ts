/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { TipomovComponent } from 'app/entities/tipomov/tipomov.component';
import { TipomovService } from 'app/entities/tipomov/tipomov.service';
import { Tipomov } from 'app/shared/model/tipomov.model';

describe('Component Tests', () => {
  describe('Tipomov Management Component', () => {
    let comp: TipomovComponent;
    let fixture: ComponentFixture<TipomovComponent>;
    let service: TipomovService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipomovComponent],
        providers: []
      })
        .overrideTemplate(TipomovComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipomovComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipomovService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tipomov(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipomovs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
