/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { RolUpdateComponent } from 'app/entities/rol/rol-update.component';
import { RolService } from 'app/entities/rol/rol.service';
import { Rol } from 'app/shared/model/rol.model';

describe('Component Tests', () => {
  describe('Rol Management Update Component', () => {
    let comp: RolUpdateComponent;
    let fixture: ComponentFixture<RolUpdateComponent>;
    let service: RolService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [RolUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RolUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RolUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rol(123);
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
        const entity = new Rol();
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
