/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SiieTestModule } from '../../../test.module';
import { PlazaDeleteDialogComponent } from 'app/entities/plaza/plaza-delete-dialog.component';
import { PlazaService } from 'app/entities/plaza/plaza.service';

describe('Component Tests', () => {
  describe('Plaza Management Delete Component', () => {
    let comp: PlazaDeleteDialogComponent;
    let fixture: ComponentFixture<PlazaDeleteDialogComponent>;
    let service: PlazaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SiieTestModule],
        declarations: [PlazaDeleteDialogComponent]
      })
        .overrideTemplate(PlazaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlazaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlazaService);
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
