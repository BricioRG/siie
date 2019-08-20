import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModulo } from 'app/shared/model/modulo.model';
import { ModuloService } from './modulo.service';

@Component({
  selector: 'jhi-modulo-delete-dialog',
  templateUrl: './modulo-delete-dialog.component.html'
})
export class ModuloDeleteDialogComponent {
  modulo: IModulo;

  constructor(protected moduloService: ModuloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.moduloService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'moduloListModification',
        content: 'Deleted an modulo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-modulo-delete-popup',
  template: ''
})
export class ModuloDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modulo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ModuloDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.modulo = modulo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/modulo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/modulo', { outlets: { popup: null } }]);
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
