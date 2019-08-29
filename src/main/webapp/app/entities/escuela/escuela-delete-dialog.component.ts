import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEscuela } from 'app/shared/model/escuela.model';
import { EscuelaService } from './escuela.service';

@Component({
  selector: 'jhi-escuela-delete-dialog',
  templateUrl: './escuela-delete-dialog.component.html'
})
export class EscuelaDeleteDialogComponent {
  escuela: IEscuela;

  constructor(protected escuelaService: EscuelaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.escuelaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'escuelaListModification',
        content: 'Deleted an escuela'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-escuela-delete-popup',
  template: ''
})
export class EscuelaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escuela }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EscuelaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.escuela = escuela;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/escuela', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/escuela', { outlets: { popup: null } }]);
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
