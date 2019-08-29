import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipomov } from 'app/shared/model/tipomov.model';
import { TipomovService } from './tipomov.service';

@Component({
  selector: 'jhi-tipomov-delete-dialog',
  templateUrl: './tipomov-delete-dialog.component.html'
})
export class TipomovDeleteDialogComponent {
  tipomov: ITipomov;

  constructor(protected tipomovService: TipomovService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipomovService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipomovListModification',
        content: 'Deleted an tipomov'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipomov-delete-popup',
  template: ''
})
export class TipomovDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipomov }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipomovDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipomov = tipomov;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipomov', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipomov', { outlets: { popup: null } }]);
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
