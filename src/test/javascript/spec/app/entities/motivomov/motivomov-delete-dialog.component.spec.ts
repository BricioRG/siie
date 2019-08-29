/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { MotivomovDeleteDialogComponent } from 'app/entities/motivomov/motivomov-delete-dialog.component';
import { MotivomovService } from 'app/entities/motivomov/motivomov.service';

describe('Component Tests', () => {
  describe('Motivomov Management Delete Component', () => {
    let comp: MotivomovDeleteDialogComponent;
    let fixture: ComponentFixture<MotivomovDeleteDialogComponent>;
    let service: MotivomovService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [MotivomovDeleteDialogComponent]
      })
        .overrideTemplate(MotivomovDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotivomovDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotivomovService);
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
