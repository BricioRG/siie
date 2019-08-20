/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { TipoperiodoComponent } from 'app/entities/tipoperiodo/tipoperiodo.component';
import { TipoperiodoService } from 'app/entities/tipoperiodo/tipoperiodo.service';
import { Tipoperiodo } from 'app/shared/model/tipoperiodo.model';

describe('Component Tests', () => {
  describe('Tipoperiodo Management Component', () => {
    let comp: TipoperiodoComponent;
    let fixture: ComponentFixture<TipoperiodoComponent>;
    let service: TipoperiodoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipoperiodoComponent],
        providers: []
      })
        .overrideTemplate(TipoperiodoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoperiodoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoperiodoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Tipoperiodo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoperiodos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
