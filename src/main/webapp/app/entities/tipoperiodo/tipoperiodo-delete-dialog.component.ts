import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';
import { TipoperiodoService } from './tipoperiodo.service';

@Component({
  selector: 'jhi-tipoperiodo-delete-dialog',
  templateUrl: './tipoperiodo-delete-dialog.component.html'
})
export class TipoperiodoDeleteDialogComponent {
  tipoperiodo: ITipoperiodo;

  constructor(
    protected tipoperiodoService: TipoperiodoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoperiodoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoperiodoListModification',
        content: 'Deleted an tipoperiodo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipoperiodo-delete-popup',
  template: ''
})
export class TipoperiodoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoperiodo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoperiodoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoperiodo = tipoperiodo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipoperiodo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipoperiodo', { outlets: { popup: null } }]);
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
