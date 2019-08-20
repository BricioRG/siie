import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModulo } from 'app/shared/model/modulo.model';

@Component({
  selector: 'jhi-modulo-detail',
  templateUrl: './modulo-detail.component.html'
})
export class ModuloDetailComponent implements OnInit {
  modulo: IModulo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modulo }) => {
      this.modulo = modulo;
    });
  }

  previousState() {
    window.history.back();
  }
}
