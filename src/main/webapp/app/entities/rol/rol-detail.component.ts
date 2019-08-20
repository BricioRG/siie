import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRol } from 'app/shared/model/rol.model';

@Component({
  selector: 'jhi-rol-detail',
  templateUrl: './rol-detail.component.html'
})
export class RolDetailComponent implements OnInit {
  rol: IRol;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rol }) => {
      this.rol = rol;
    });
  }

  previousState() {
    window.history.back();
  }
}
