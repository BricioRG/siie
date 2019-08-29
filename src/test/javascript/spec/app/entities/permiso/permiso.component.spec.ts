/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SiieTestModule } from '../../../test.module';
import { PermisoComponent } from 'app/entities/permiso/permiso.component';
import { PermisoService } from 'app/entities/permiso/permiso.service';
import { Permiso } from 'app/shared/model/permiso.model';

describe('Component Tests', () => {
  describe('Permiso Management Component', () => {
    let comp: PermisoComponent;
    let fixture: ComponentFixture<PermisoComponent>;
    let service: PermisoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PermisoComponent],
        providers: []
      })
        .overrideTemplate(PermisoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermisoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermisoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Permiso(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.permisos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
