/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { ModuloUpdateComponent } from 'app/entities/modulo/modulo-update.component';
import { ModuloService } from 'app/entities/modulo/modulo.service';
import { Modulo } from 'app/shared/model/modulo.model';

describe('Component Tests', () => {
  describe('Modulo Management Update Component', () => {
    let comp: ModuloUpdateComponent;
    let fixture: ComponentFixture<ModuloUpdateComponent>;
    let service: ModuloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [ModuloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ModuloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModuloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModuloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Modulo(123);
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
        const entity = new Modulo();
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
