import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEscuela, Escuela } from 'app/shared/model/escuela.model';
import { EscuelaService } from './escuela.service';

@Component({
  selector: 'jhi-escuela-update',
  templateUrl: './escuela-update.component.html'
})
export class EscuelaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cct: [],
    nombre: []
  });

  constructor(protected escuelaService: EscuelaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ escuela }) => {
      this.updateForm(escuela);
    });
  }

  updateForm(escuela: IEscuela) {
    this.editForm.patchValue({
      id: escuela.id,
      cct: escuela.cct,
      nombre: escuela.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const escuela = this.createFromForm();
    if (escuela.id !== undefined) {
      this.subscribeToSaveResponse(this.escuelaService.update(escuela));
    } else {
      this.subscribeToSaveResponse(this.escuelaService.create(escuela));
    }
  }

  private createFromForm(): IEscuela {
    return {
      ...new Escuela(),
      id: this.editForm.get(['id']).value,
      cct: this.editForm.get(['cct']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscuela>>) {
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
