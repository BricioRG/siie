/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { ModuloComponent } from 'app/entities/modulo/modulo.component';
import { ModuloService } from 'app/entities/modulo/modulo.service';
import { Modulo } from 'app/shared/model/modulo.model';

describe('Component Tests', () => {
  describe('Modulo Management Component', () => {
    let comp: ModuloComponent;
    let fixture: ComponentFixture<ModuloComponent>;
    let service: ModuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [ModuloComponent],
        providers: []
      })
        .overrideTemplate(ModuloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModuloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModuloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Modulo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modulos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
