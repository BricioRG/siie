import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJornada } from 'app/shared/model/jornada.model';
import { JornadaService } from './jornada.service';

@Component({
  selector: 'jhi-jornada-delete-dialog',
  templateUrl: './jornada-delete-dialog.component.html'
})
export class JornadaDeleteDialogComponent {
  jornada: IJornada;

  constructor(protected jornadaService: JornadaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jornadaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'jornadaListModification',
        content: 'Deleted an jornada'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-jornada-delete-popup',
  template: ''
})
export class JornadaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jornada }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(JornadaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.jornada = jornada;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/jornada', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/jornada', { outlets: { popup: null } }]);
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
