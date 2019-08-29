import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';

@Component({
  selector: 'jhi-funcionlaboral-detail',
  templateUrl: './funcionlaboral-detail.component.html'
})
export class FuncionlaboralDetailComponent implements OnInit {
  funcionlaboral: IFuncionlaboral;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funcionlaboral }) => {
      this.funcionlaboral = funcionlaboral;
    });
  }

  previousState() {
    window.history.back();
  }
}
