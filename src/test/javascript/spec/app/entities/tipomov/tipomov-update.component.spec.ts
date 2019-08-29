/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { TipomovUpdateComponent } from 'app/entities/tipomov/tipomov-update.component';
import { TipomovService } from 'app/entities/tipomov/tipomov.service';
import { Tipomov } from 'app/shared/model/tipomov.model';

describe('Component Tests', () => {
  describe('Tipomov Management Update Component', () => {
    let comp: TipomovUpdateComponent;
    let fixture: ComponentFixture<TipomovUpdateComponent>;
    let service: TipomovService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipomovUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipomovUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipomovUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipomovService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tipomov(123);
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
        const entity = new Tipomov();
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
