/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { PermisoDeleteDialogComponent } from 'app/entities/permiso/permiso-delete-dialog.component';
import { PermisoService } from 'app/entities/permiso/permiso.service';

describe('Component Tests', () => {
  describe('Permiso Management Delete Component', () => {
    let comp: PermisoDeleteDialogComponent;
    let fixture: ComponentFixture<PermisoDeleteDialogComponent>;
    let service: PermisoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PermisoDeleteDialogComponent]
      })
        .overrideTemplate(PermisoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermisoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermisoService);
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
