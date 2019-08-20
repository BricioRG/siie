import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMotivomov, Motivomov } from 'app/shared/model/motivomov.model';
import { MotivomovService } from './motivomov.service';

@Component({
  selector: 'jhi-motivomov-update',
  templateUrl: './motivomov-update.component.html'
})
export class MotivomovUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clave: [],
    descripcion: []
  });

  constructor(protected motivomovService: MotivomovService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ motivomov }) => {
      this.updateForm(motivomov);
    });
  }

  updateForm(motivomov: IMotivomov) {
    this.editForm.patchValue({
      id: motivomov.id,
      clave: motivomov.clave,
      descripcion: motivomov.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const motivomov = this.createFromForm();
    if (motivomov.id !== undefined) {
      this.subscribeToSaveResponse(this.motivomovService.update(motivomov));
    } else {
      this.subscribeToSaveResponse(this.motivomovService.create(motivomov));
    }
  }

  private createFromForm(): IMotivomov {
    return {
      ...new Motivomov(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMotivomov>>) {
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
