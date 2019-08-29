import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscolaridad } from 'app/shared/model/escolaridad.model';
import { EscolaridadService } from './escolaridad.service';

@Component({
  selector: 'jhi-escolaridad-delete-dialog',
  templateUrl: './escolaridad-delete-dialog.component.html'
})
export class EscolaridadDeleteDialogComponent {
  escolaridad: IEscolaridad;

  constructor(
    protected escolaridadService: EscolaridadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.escolaridadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'escolaridadListModification',
        content: 'Deleted an escolaridad'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-escolaridad-delete-popup',
  template: ''
})
export class EscolaridadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escolaridad }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EscolaridadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.escolaridad = escolaridad;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/escolaridad', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/escolaridad', { outlets: { popup: null } }]);
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
