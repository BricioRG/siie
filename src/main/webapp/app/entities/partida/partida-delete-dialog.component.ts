import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartida } from 'app/shared/model/partida.model';
import { PartidaService } from './partida.service';

@Component({
  selector: 'jhi-partida-delete-dialog',
  templateUrl: './partida-delete-dialog.component.html'
})
export class PartidaDeleteDialogComponent {
  partida: IPartida;

  constructor(protected partidaService: PartidaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.partidaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'partidaListModification',
        content: 'Deleted an partida'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-partida-delete-popup',
  template: ''
})
export class PartidaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partida }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PartidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.partida = partida;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/partida', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/partida', { outlets: { popup: null } }]);
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
