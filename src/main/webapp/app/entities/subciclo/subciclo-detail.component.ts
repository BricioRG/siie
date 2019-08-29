import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubciclo } from 'app/shared/model/subciclo.model';

@Component({
  selector: 'jhi-subciclo-detail',
  templateUrl: './subciclo-detail.component.html'
})
export class SubcicloDetailComponent implements OnInit {
  subciclo: ISubciclo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subciclo }) => {
      this.subciclo = subciclo;
    });
  }

  previousState() {
    window.history.back();
  }
}
