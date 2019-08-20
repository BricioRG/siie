/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { PermisoUpdateComponent } from 'app/entities/permiso/permiso-update.component';
import { PermisoService } from 'app/entities/permiso/permiso.service';
import { Permiso } from 'app/shared/model/permiso.model';

describe('Component Tests', () => {
  describe('Permiso Management Update Component', () => {
    let comp: PermisoUpdateComponent;
    let fixture: ComponentFixture<PermisoUpdateComponent>;
    let service: PermisoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PermisoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PermisoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermisoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermisoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Permiso(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Permiso();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
