/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { MotivomovUpdateComponent } from 'app/entities/motivomov/motivomov-update.component';
import { MotivomovService } from 'app/entities/motivomov/motivomov.service';
import { Motivomov } from 'app/shared/model/motivomov.model';

describe('Component Tests', () => {
  describe('Motivomov Management Update Component', () => {
    let comp: MotivomovUpdateComponent;
    let fixture: ComponentFixture<MotivomovUpdateComponent>;
    let service: MotivomovService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [MotivomovUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MotivomovUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotivomovUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotivomovService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Motivomov(123);
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
        const entity = new Motivomov();
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
