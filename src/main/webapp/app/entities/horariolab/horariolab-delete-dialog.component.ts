import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHorariolab } from 'app/shared/model/horariolab.model';
import { HorariolabService } from './horariolab.service';

@Component({
  selector: 'jhi-horariolab-delete-dialog',
  templateUrl: './horariolab-delete-dialog.component.html'
})
export class HorariolabDeleteDialogComponent {
  horariolab: IHorariolab;

  constructor(
    protected horariolabService: HorariolabService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.horariolabService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'horariolabListModification',
        content: 'Deleted an horariolab'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-horariolab-delete-popup',
  template: ''
})
export class HorariolabDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ horariolab }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HorariolabDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.horariolab = horariolab;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/horariolab', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/horariolab', { outlets: { popup: null } }]);
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
