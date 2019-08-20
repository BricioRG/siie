/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { SubcicloUpdateComponent } from 'app/entities/subciclo/subciclo-update.component';
import { SubcicloService } from 'app/entities/subciclo/subciclo.service';
import { Subciclo } from 'app/shared/model/subciclo.model';

describe('Component Tests', () => {
  describe('Subciclo Management Update Component', () => {
    let comp: SubcicloUpdateComponent;
    let fixture: ComponentFixture<SubcicloUpdateComponent>;
    let service: SubcicloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [SubcicloUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubcicloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcicloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcicloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Subciclo(123);
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
        const entity = new Subciclo();
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
