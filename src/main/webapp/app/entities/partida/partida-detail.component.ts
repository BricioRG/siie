import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartida } from 'app/shared/model/partida.model';

@Component({
  selector: 'jhi-partida-detail',
  templateUrl: './partida-detail.component.html'
})
export class PartidaDetailComponent implements OnInit {
  partida: IPartida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partida }) => {
      this.partida = partida;
    });
  }

  previousState() {
    window.history.back();
  }
}
