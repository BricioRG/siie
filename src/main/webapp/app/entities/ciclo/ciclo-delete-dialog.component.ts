import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICiclo } from 'app/shared/model/ciclo.model';
import { CicloService } from './ciclo.service';

@Component({
  selector: 'jhi-ciclo-delete-dialog',
  templateUrl: './ciclo-delete-dialog.component.html'
})
export class CicloDeleteDialogComponent {
  ciclo: ICiclo;

  constructor(protected cicloService: CicloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cicloService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cicloListModification',
        content: 'Deleted an ciclo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ciclo-delete-popup',
  template: ''
})
export class CicloDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ciclo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CicloDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ciclo = ciclo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ciclo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ciclo', { outlets: { popup: null } }]);
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
