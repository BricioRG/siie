import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMotivomov } from 'app/shared/model/motivomov.model';

@Component({
  selector: 'jhi-motivomov-detail',
  templateUrl: './motivomov-detail.component.html'
})
export class MotivomovDetailComponent implements OnInit {
  motivomov: IMotivomov;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ motivomov }) => {
      this.motivomov = motivomov;
    });
  }

  previousState() {
    window.history.back();
  }
}
