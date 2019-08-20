import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlaza } from 'app/shared/model/plaza.model';
import { PlazaService } from './plaza.service';

@Component({
  selector: 'jhi-plaza-delete-dialog',
  templateUrl: './plaza-delete-dialog.component.html'
})
export class PlazaDeleteDialogComponent {
  plaza: IPlaza;

  constructor(protected plazaService: PlazaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.plazaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'plazaListModification',
        content: 'Deleted an plaza'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-plaza-delete-popup',
  template: ''
})
export class PlazaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plaza }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PlazaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.plaza = plaza;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/plaza', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/plaza', { outlets: { popup: null } }]);
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
