/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { FinanciamientoDeleteDialogComponent } from 'app/entities/financiamiento/financiamiento-delete-dialog.component';
import { FinanciamientoService } from 'app/entities/financiamiento/financiamiento.service';

describe('Component Tests', () => {
  describe('Financiamiento Management Delete Component', () => {
    let comp: FinanciamientoDeleteDialogComponent;
    let fixture: ComponentFixture<FinanciamientoDeleteDialogComponent>;
    let service: FinanciamientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [FinanciamientoDeleteDialogComponent]
      })
        .overrideTemplate(FinanciamientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinanciamientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanciamientoService);
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
