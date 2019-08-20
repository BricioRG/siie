import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMotivomov } from 'app/shared/model/motivomov.model';
import { MotivomovService } from './motivomov.service';

@Component({
  selector: 'jhi-motivomov-delete-dialog',
  templateUrl: './motivomov-delete-dialog.component.html'
})
export class MotivomovDeleteDialogComponent {
  motivomov: IMotivomov;

  constructor(protected motivomovService: MotivomovService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.motivomovService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'motivomovListModification',
        content: 'Deleted an motivomov'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-motivomov-delete-popup',
  template: ''
})
export class MotivomovDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ motivomov }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MotivomovDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.motivomov = motivomov;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/motivomov', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/motivomov', { outlets: { popup: null } }]);
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
