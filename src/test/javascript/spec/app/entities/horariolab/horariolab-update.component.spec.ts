/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { HorariolabUpdateComponent } from 'app/entities/horariolab/horariolab-update.component';
import { HorariolabService } from 'app/entities/horariolab/horariolab.service';
import { Horariolab } from 'app/shared/model/horariolab.model';

describe('Component Tests', () => {
  describe('Horariolab Management Update Component', () => {
    let comp: HorariolabUpdateComponent;
    let fixture: ComponentFixture<HorariolabUpdateComponent>;
    let service: HorariolabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [HorariolabUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HorariolabUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HorariolabUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorariolabService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Horariolab(123);
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
        const entity = new Horariolab();
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
