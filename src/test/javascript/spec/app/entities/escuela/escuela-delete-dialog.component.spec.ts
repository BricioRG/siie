/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { EscuelaDeleteDialogComponent } from 'app/entities/escuela/escuela-delete-dialog.component';
import { EscuelaService } from 'app/entities/escuela/escuela.service';

describe('Component Tests', () => {
  describe('Escuela Management Delete Component', () => {
    let comp: EscuelaDeleteDialogComponent;
    let fixture: ComponentFixture<EscuelaDeleteDialogComponent>;
    let service: EscuelaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [EscuelaDeleteDialogComponent]
      })
        .overrideTemplate(EscuelaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EscuelaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscuelaService);
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
