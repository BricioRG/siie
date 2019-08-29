import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJornada } from 'app/shared/model/jornada.model';

@Component({
  selector: 'jhi-jornada-detail',
  templateUrl: './jornada-detail.component.html'
})
export class JornadaDetailComponent implements OnInit {
  jornada: IJornada;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jornada }) => {
      this.jornada = jornada;
    });
  }

  previousState() {
    window.history.back();
  }
}
