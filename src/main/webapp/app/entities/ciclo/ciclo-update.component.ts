import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICiclo, Ciclo } from 'app/shared/model/ciclo.model';
import { CicloService } from './ciclo.service';

@Component({
  selector: 'jhi-ciclo-update',
  templateUrl: './ciclo-update.component.html'
})
export class CicloUpdateComponent implements OnInit {
  isSaving: boolean;
  feciniDp: any;
  fecfinDp: any;

  editForm = this.fb.group({
    id: [],
    clave: [],
    descripcion: [],
    fecini: [],
    fecfin: []
  });

  constructor(protected cicloService: CicloService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ciclo }) => {
      this.updateForm(ciclo);
    });
  }

  updateForm(ciclo: ICiclo) {
    this.editForm.patchValue({
      id: ciclo.id,
      clave: ciclo.clave,
      descripcion: ciclo.descripcion,
      fecini: ciclo.fecini,
      fecfin: ciclo.fecfin
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ciclo = this.createFromForm();
    if (ciclo.id !== undefined) {
      this.subscribeToSaveResponse(this.cicloService.update(ciclo));
    } else {
      this.subscribeToSaveResponse(this.cicloService.create(ciclo));
    }
  }

  private createFromForm(): ICiclo {
    return {
      ...new Ciclo(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fecini: this.editForm.get(['fecini']).value,
      fecfin: this.editForm.get(['fecfin']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiclo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
