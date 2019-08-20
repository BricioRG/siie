/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { JornadaUpdateComponent } from 'app/entities/jornada/jornada-update.component';
import { JornadaService } from 'app/entities/jornada/jornada.service';
import { Jornada } from 'app/shared/model/jornada.model';

describe('Component Tests', () => {
  describe('Jornada Management Update Component', () => {
    let comp: JornadaUpdateComponent;
    let fixture: ComponentFixture<JornadaUpdateComponent>;
    let service: JornadaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [JornadaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JornadaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JornadaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JornadaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jornada(123);
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
        const entity = new Jornada();
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
