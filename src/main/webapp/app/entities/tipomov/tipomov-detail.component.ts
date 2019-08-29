import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipomov } from 'app/shared/model/tipomov.model';

@Component({
  selector: 'jhi-tipomov-detail',
  templateUrl: './tipomov-detail.component.html'
})
export class TipomovDetailComponent implements OnInit {
  tipomov: ITipomov;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipomov }) => {
      this.tipomov = tipomov;
    });
  }

  previousState() {
    window.history.back();
  }
}
