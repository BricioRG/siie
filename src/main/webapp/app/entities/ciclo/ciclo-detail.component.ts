import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICiclo } from 'app/shared/model/ciclo.model';

@Component({
  selector: 'jhi-ciclo-detail',
  templateUrl: './ciclo-detail.component.html'
})
export class CicloDetailComponent implements OnInit {
  ciclo: ICiclo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ciclo }) => {
      this.ciclo = ciclo;
    });
  }

  previousState() {
    window.history.back();
  }
}
