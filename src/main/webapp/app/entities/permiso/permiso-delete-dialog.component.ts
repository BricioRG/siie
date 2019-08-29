import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPermiso } from 'app/shared/model/permiso.model';
import { PermisoService } from './permiso.service';

@Component({
  selector: 'jhi-permiso-delete-dialog',
  templateUrl: './permiso-delete-dialog.component.html'
})
export class PermisoDeleteDialogComponent {
  permiso: IPermiso;

  constructor(protected permisoService: PermisoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.permisoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'permisoListModification',
        content: 'Deleted an permiso'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-permiso-delete-popup',
  template: ''
})
export class PermisoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ permiso }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PermisoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.permiso = permiso;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/permiso', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/permiso', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
