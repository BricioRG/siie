/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { TipomovDeleteDialogComponent } from 'app/entities/tipomov/tipomov-delete-dialog.component';
import { TipomovService } from 'app/entities/tipomov/tipomov.service';

describe('Component Tests', () => {
  describe('Tipomov Management Delete Component', () => {
    let comp: TipomovDeleteDialogComponent;
    let fixture: ComponentFixture<TipomovDeleteDialogComponent>;
    let service: TipomovService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipomovDeleteDialogComponent]
      })
        .overrideTemplate(TipomovDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipomovDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipomovService);
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
