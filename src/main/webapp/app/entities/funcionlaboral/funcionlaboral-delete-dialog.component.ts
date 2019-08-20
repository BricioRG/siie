import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';
import { FuncionlaboralService } from './funcionlaboral.service';

@Component({
  selector: 'jhi-funcionlaboral-delete-dialog',
  templateUrl: './funcionlaboral-delete-dialog.component.html'
})
export class FuncionlaboralDeleteDialogComponent {
  funcionlaboral: IFuncionlaboral;

  constructor(
    protected funcionlaboralService: FuncionlaboralService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.funcionlaboralService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'funcionlaboralListModification',
        content: 'Deleted an funcionlaboral'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-funcionlaboral-delete-popup',
  template: ''
})
export class FuncionlaboralDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funcionlaboral }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuncionlaboralDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.funcionlaboral = funcionlaboral;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/funcionlaboral', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/funcionlaboral', { outlets: { popup: null } }]);
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
