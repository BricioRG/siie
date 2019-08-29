/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { PlazaUpdateComponent } from 'app/entities/plaza/plaza-update.component';
import { PlazaService } from 'app/entities/plaza/plaza.service';
import { Plaza } from 'app/shared/model/plaza.model';

describe('Component Tests', () => {
  describe('Plaza Management Update Component', () => {
    let comp: PlazaUpdateComponent;
    let fixture: ComponentFixture<PlazaUpdateComponent>;
    let service: PlazaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PlazaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlazaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlazaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlazaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Plaza(123);
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
        const entity = new Plaza();
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
