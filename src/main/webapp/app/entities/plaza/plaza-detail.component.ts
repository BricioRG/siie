import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlaza } from 'app/shared/model/plaza.model';

@Component({
  selector: 'jhi-plaza-detail',
  templateUrl: './plaza-detail.component.html'
})
export class PlazaDetailComponent implements OnInit {
  plaza: IPlaza;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plaza }) => {
      this.plaza = plaza;
    });
  }

  previousState() {
    window.history.back();
  }
}
