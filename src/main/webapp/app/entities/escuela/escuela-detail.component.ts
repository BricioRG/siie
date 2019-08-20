import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEscuela } from 'app/shared/model/escuela.model';

@Component({
  selector: 'jhi-escuela-detail',
  templateUrl: './escuela-detail.component.html'
})
export class EscuelaDetailComponent implements OnInit {
  escuela: IEscuela;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escuela }) => {
      this.escuela = escuela;
    });
  }

  previousState() {
    window.history.back();
  }
}
