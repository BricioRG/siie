/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { HorariolabDeleteDialogComponent } from 'app/entities/horariolab/horariolab-delete-dialog.component';
import { HorariolabService } from 'app/entities/horariolab/horariolab.service';

describe('Component Tests', () => {
  describe('Horariolab Management Delete Component', () => {
    let comp: HorariolabDeleteDialogComponent;
    let fixture: ComponentFixture<HorariolabDeleteDialogComponent>;
    let service: HorariolabService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [HorariolabDeleteDialogComponent]
      })
        .overrideTemplate(HorariolabDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HorariolabDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HorariolabService);
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
