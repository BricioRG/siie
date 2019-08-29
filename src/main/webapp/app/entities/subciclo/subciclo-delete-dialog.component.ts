import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubciclo } from 'app/shared/model/subciclo.model';
import { SubcicloService } from './subciclo.service';

@Component({
  selector: 'jhi-subciclo-delete-dialog',
  templateUrl: './subciclo-delete-dialog.component.html'
})
export class SubcicloDeleteDialogComponent {
  subciclo: ISubciclo;

  constructor(protected subcicloService: SubcicloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subcicloService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'subcicloListModification',
        content: 'Deleted an subciclo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subciclo-delete-popup',
  template: ''
})
export class SubcicloDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subciclo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubcicloDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subciclo = subciclo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/subciclo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/subciclo', { outlets: { popup: null } }]);
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
