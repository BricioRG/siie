/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SiieTestModule } from '../../../test.module';
import { FuncionlaboralUpdateComponent } from 'app/entities/funcionlaboral/funcionlaboral-update.component';
import { FuncionlaboralService } from 'app/entities/funcionlaboral/funcionlaboral.service';
import { Funcionlaboral } from 'app/shared/model/funcionlaboral.model';

describe('Component Tests', () => {
  describe('Funcionlaboral Management Update Component', () => {
    let comp: FuncionlaboralUpdateComponent;
    let fixture: ComponentFixture<FuncionlaboralUpdateComponent>;
    let service: FuncionlaboralService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FuncionlaboralUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuncionlaboralUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuncionlaboralUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuncionlaboralService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Funcionlaboral(123);
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
        const entity = new Funcionlaboral();
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
