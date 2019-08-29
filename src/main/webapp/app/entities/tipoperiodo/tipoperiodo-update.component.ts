import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoperiodo, Tipoperiodo } from 'app/shared/model/tipoperiodo.model';
import { TipoperiodoService } from './tipoperiodo.service';

@Component({
  selector: 'jhi-tipoperiodo-update',
  templateUrl: './tipoperiodo-update.component.html'
})
export class TipoperiodoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected tipoperiodoService: TipoperiodoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoperiodo }) => {
      this.updateForm(tipoperiodo);
    });
  }

  updateForm(tipoperiodo: ITipoperiodo) {
    this.editForm.patchValue({
      id: tipoperiodo.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoperiodo = this.createFromForm();
    if (tipoperiodo.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoperiodoService.update(tipoperiodo));
    } else {
      this.subscribeToSaveResponse(this.tipoperiodoService.create(tipoperiodo));
    }
  }

  private createFromForm(): ITipoperiodo {
    return {
      ...new Tipoperiodo(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoperiodo>>) {
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
