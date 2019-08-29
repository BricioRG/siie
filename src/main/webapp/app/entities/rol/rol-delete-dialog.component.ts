import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRol } from 'app/shared/model/rol.model';
import { RolService } from './rol.service';

@Component({
  selector: 'jhi-rol-delete-dialog',
  templateUrl: './rol-delete-dialog.component.html'
})
export class RolDeleteDialogComponent {
  rol: IRol;

  constructor(protected rolService: RolService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rolService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rolListModification',
        content: 'Deleted an rol'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-rol-delete-popup',
  template: ''
})
export class RolDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rol }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RolDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.rol = rol;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/rol', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/rol', { outlets: { popup: null } }]);
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
