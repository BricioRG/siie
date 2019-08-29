/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { TipoperiodoUpdateComponent } from 'app/entities/tipoperiodo/tipoperiodo-update.component';
import { TipoperiodoService } from 'app/entities/tipoperiodo/tipoperiodo.service';
import { Tipoperiodo } from 'app/shared/model/tipoperiodo.model';

describe('Component Tests', () => {
  describe('Tipoperiodo Management Update Component', () => {
    let comp: TipoperiodoUpdateComponent;
    let fixture: ComponentFixture<TipoperiodoUpdateComponent>;
    let service: TipoperiodoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipoperiodoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoperiodoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoperiodoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoperiodoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tipoperiodo(123);
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
        const entity = new Tipoperiodo();
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
