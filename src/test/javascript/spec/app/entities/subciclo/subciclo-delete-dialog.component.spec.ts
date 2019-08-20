/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { SubcicloDeleteDialogComponent } from 'app/entities/subciclo/subciclo-delete-dialog.component';
import { SubcicloService } from 'app/entities/subciclo/subciclo.service';

describe('Component Tests', () => {
  describe('Subciclo Management Delete Component', () => {
    let comp: SubcicloDeleteDialogComponent;
    let fixture: ComponentFixture<SubcicloDeleteDialogComponent>;
    let service: SubcicloService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [SubcicloDeleteDialogComponent]
      })
        .overrideTemplate(SubcicloDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubcicloDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcicloService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
