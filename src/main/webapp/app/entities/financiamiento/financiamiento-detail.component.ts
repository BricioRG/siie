import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinanciamiento } from 'app/shared/model/financiamiento.model';

@Component({
  selector: 'jhi-financiamiento-detail',
  templateUrl: './financiamiento-detail.component.html'
})
export class FinanciamientoDetailComponent implements OnInit {
  financiamiento: IFinanciamiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ financiamiento }) => {
      this.financiamiento = financiamiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
