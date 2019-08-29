/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { EscuelaUpdateComponent } from 'app/entities/escuela/escuela-update.component';
import { EscuelaService } from 'app/entities/escuela/escuela.service';
import { Escuela } from 'app/shared/model/escuela.model';

describe('Component Tests', () => {
  describe('Escuela Management Update Component', () => {
    let comp: EscuelaUpdateComponent;
    let fixture: ComponentFixture<EscuelaUpdateComponent>;
    let service: EscuelaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscuelaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EscuelaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscuelaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscuelaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Escuela(123);
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
        const entity = new Escuela();
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
