import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinanciamiento } from 'app/shared/model/financiamiento.model';
import { FinanciamientoService } from './financiamiento.service';

@Component({
  selector: 'jhi-financiamiento-delete-dialog',
  templateUrl: './financiamiento-delete-dialog.component.html'
})
export class FinanciamientoDeleteDialogComponent {
  financiamiento: IFinanciamiento;

  constructor(
    protected financiamientoService: FinanciamientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.financiamientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'financiamientoListModification',
        content: 'Deleted an financiamiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-financiamiento-delete-popup',
  template: ''
})
export class FinanciamientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ financiamiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FinanciamientoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.financiamiento = financiamiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/financiamiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/financiamiento', { outlets: { popup: null } }]);
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
