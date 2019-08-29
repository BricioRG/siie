import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPersona, Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html'
})
export class PersonaUpdateComponent implements OnInit {
  isSaving: boolean;
  fechanacDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    primerape: [],
    segundoape: [],
    fechanac: [],
    entidadnac: [],
    genero: [],
    rfc: [],
    curp: [],
    edocivil: [],
    empleado: [],
    nuempleado: []
  });

  constructor(protected personaService: PersonaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);
    });
  }

  updateForm(persona: IPersona) {
    this.editForm.patchValue({
      id: persona.id,
      nombre: persona.nombre,
      primerape: persona.primerape,
      segundoape: persona.segundoape,
      fechanac: persona.fechanac,
      entidadnac: persona.entidadnac,
      genero: persona.genero,
      rfc: persona.rfc,
      curp: persona.curp,
      edocivil: persona.edocivil,
      empleado: persona.empleado,
      nuempleado: persona.nuempleado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  private createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      primerape: this.editForm.get(['primerape']).value,
      segundoape: this.editForm.get(['segundoape']).value,
      fechanac: this.editForm.get(['fechanac']).value,
      entidadnac: this.editForm.get(['entidadnac']).value,
      genero: this.editForm.get(['genero']).value,
      rfc: this.editForm.get(['rfc']).value,
      curp: this.editForm.get(['curp']).value,
      edocivil: this.editForm.get(['edocivil']).value,
      empleado: this.editForm.get(['empleado']).value,
      nuempleado: this.editForm.get(['nuempleado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>) {
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
