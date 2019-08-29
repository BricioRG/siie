import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IJornada, Jornada } from 'app/shared/model/jornada.model';
import { JornadaService } from './jornada.service';

@Component({
  selector: 'jhi-jornada-update',
  templateUrl: './jornada-update.component.html'
})
export class JornadaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clave: [],
    descripcion: []
  });

  constructor(protected jornadaService: JornadaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jornada }) => {
      this.updateForm(jornada);
    });
  }

  updateForm(jornada: IJornada) {
    this.editForm.patchValue({
      id: jornada.id,
      clave: jornada.clave,
      descripcion: jornada.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jornada = this.createFromForm();
    if (jornada.id !== undefined) {
      this.subscribeToSaveResponse(this.jornadaService.update(jornada));
    } else {
      this.subscribeToSaveResponse(this.jornadaService.create(jornada));
    }
  }

  private createFromForm(): IJornada {
    return {
      ...new Jornada(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJornada>>) {
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
