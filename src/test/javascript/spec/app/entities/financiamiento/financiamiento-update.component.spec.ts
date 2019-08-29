/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { FinanciamientoUpdateComponent } from 'app/entities/financiamiento/financiamiento-update.component';
import { FinanciamientoService } from 'app/entities/financiamiento/financiamiento.service';
import { Financiamiento } from 'app/shared/model/financiamiento.model';

describe('Component Tests', () => {
  describe('Financiamiento Management Update Component', () => {
    let comp: FinanciamientoUpdateComponent;
    let fixture: ComponentFixture<FinanciamientoUpdateComponent>;
    let service: FinanciamientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FinanciamientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FinanciamientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinanciamientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanciamientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Financiamiento(123);
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
        const entity = new Financiamiento();
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
