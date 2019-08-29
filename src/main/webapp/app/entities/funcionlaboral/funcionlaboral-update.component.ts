import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFuncionlaboral, Funcionlaboral } from 'app/shared/model/funcionlaboral.model';
import { FuncionlaboralService } from './funcionlaboral.service';

@Component({
  selector: 'jhi-funcionlaboral-update',
  templateUrl: './funcionlaboral-update.component.html'
})
export class FuncionlaboralUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clave: [],
    descripcion: []
  });

  constructor(protected funcionlaboralService: FuncionlaboralService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ funcionlaboral }) => {
      this.updateForm(funcionlaboral);
    });
  }

  updateForm(funcionlaboral: IFuncionlaboral) {
    this.editForm.patchValue({
      id: funcionlaboral.id,
      clave: funcionlaboral.clave,
      descripcion: funcionlaboral.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const funcionlaboral = this.createFromForm();
    if (funcionlaboral.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionlaboralService.update(funcionlaboral));
    } else {
      this.subscribeToSaveResponse(this.funcionlaboralService.create(funcionlaboral));
    }
  }

  private createFromForm(): IFuncionlaboral {
    return {
      ...new Funcionlaboral(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionlaboral>>) {
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
