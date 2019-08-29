import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEscolaridad } from 'app/shared/model/escolaridad.model';

@Component({
  selector: 'jhi-escolaridad-detail',
  templateUrl: './escolaridad-detail.component.html'
})
export class EscolaridadDetailComponent implements OnInit {
  escolaridad: IEscolaridad;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escolaridad }) => {
      this.escolaridad = escolaridad;
    });
  }

  previousState() {
    window.history.back();
  }
}
