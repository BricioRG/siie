/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { EscolaridadUpdateComponent } from 'app/entities/escolaridad/escolaridad-update.component';
import { EscolaridadService } from 'app/entities/escolaridad/escolaridad.service';
import { Escolaridad } from 'app/shared/model/escolaridad.model';

describe('Component Tests', () => {
  describe('Escolaridad Management Update Component', () => {
    let comp: EscolaridadUpdateComponent;
    let fixture: ComponentFixture<EscolaridadUpdateComponent>;
    let service: EscolaridadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscolaridadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EscolaridadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscolaridadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscolaridadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Escolaridad(123);
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
        const entity = new Escolaridad();
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
