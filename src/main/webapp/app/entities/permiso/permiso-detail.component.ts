import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPermiso } from 'app/shared/model/permiso.model';

@Component({
  selector: 'jhi-permiso-detail',
  templateUrl: './permiso-detail.component.html'
})
export class PermisoDetailComponent implements OnInit {
  permiso: IPermiso;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ permiso }) => {
      this.permiso = permiso;
    });
  }

  previousState() {
    window.history.back();
  }
}
