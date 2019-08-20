import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorariolab } from 'app/shared/model/horariolab.model';

@Component({
  selector: 'jhi-horariolab-detail',
  templateUrl: './horariolab-detail.component.html'
})
export class HorariolabDetailComponent implements OnInit {
  horariolab: IHorariolab;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ horariolab }) => {
      this.horariolab = horariolab;
    });
  }

  previousState() {
    window.history.back();
  }
}
