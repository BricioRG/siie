import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFinanciamiento, Financiamiento } from 'app/shared/model/financiamiento.model';
import { FinanciamientoService } from './financiamiento.service';

@Component({
  selector: 'jhi-financiamiento-update',
  templateUrl: './financiamiento-update.component.html'
})
export class FinanciamientoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clave: [],
    descripcion: []
  });

  constructor(protected financiamientoService: FinanciamientoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ financiamiento }) => {
      this.updateForm(financiamiento);
    });
  }

  updateForm(financiamiento: IFinanciamiento) {
    this.editForm.patchValue({
      id: financiamiento.id,
      clave: financiamiento.clave,
      descripcion: financiamiento.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const financiamiento = this.createFromForm();
    if (financiamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.financiamientoService.update(financiamiento));
    } else {
      this.subscribeToSaveResponse(this.financiamientoService.create(financiamiento));
    }
  }

  private createFromForm(): IFinanciamiento {
    return {
      ...new Financiamiento(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinanciamiento>>) {
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
