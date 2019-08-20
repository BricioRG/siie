/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { TipoperiodoDeleteDialogComponent } from 'app/entities/tipoperiodo/tipoperiodo-delete-dialog.component';
import { TipoperiodoService } from 'app/entities/tipoperiodo/tipoperiodo.service';

describe('Component Tests', () => {
  describe('Tipoperiodo Management Delete Component', () => {
    let comp: TipoperiodoDeleteDialogComponent;
    let fixture: ComponentFixture<TipoperiodoDeleteDialogComponent>;
    let service: TipoperiodoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [TipoperiodoDeleteDialogComponent]
      })
        .overrideTemplate(TipoperiodoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoperiodoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoperiodoService);
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
