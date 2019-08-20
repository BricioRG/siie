import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';

@Component({
  selector: 'jhi-tipoperiodo-detail',
  templateUrl: './tipoperiodo-detail.component.html'
})
export class TipoperiodoDetailComponent implements OnInit {
  tipoperiodo: ITipoperiodo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoperiodo }) => {
      this.tipoperiodo = tipoperiodo;
    });
  }

  previousState() {
    window.history.back();
  }
}
