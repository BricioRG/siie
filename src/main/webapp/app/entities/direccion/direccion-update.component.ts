import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDireccion, Direccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona';

@Component({
  selector: 'jhi-direccion-update',
  templateUrl: './direccion-update.component.html'
})
export class DireccionUpdateComponent implements OnInit {
  isSaving: boolean;

  personas: IPersona[];

  editForm = this.fb.group({
    id: [],
    calle: [],
    numeroint: [],
    numeroext: [],
    colonia: [],
    ciudad: [],
    municipio: [],
    estado: [],
    referencia: [],
    entrecalle: [],
    persona: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected direccionService: DireccionService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.updateForm(direccion);
    });
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(direccion: IDireccion) {
    this.editForm.patchValue({
      id: direccion.id,
      calle: direccion.calle,
      numeroint: direccion.numeroint,
      numeroext: direccion.numeroext,
      colonia: direccion.colonia,
      ciudad: direccion.ciudad,
      municipio: direccion.municipio,
      estado: direccion.estado,
      referencia: direccion.referencia,
      entrecalle: direccion.entrecalle,
      persona: direccion.persona
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const direccion = this.createFromForm();
    if (direccion.id !== undefined) {
      this.subscribeToSaveResponse(this.direccionService.update(direccion));
    } else {
      this.subscribeToSaveResponse(this.direccionService.create(direccion));
    }
  }

  private createFromForm(): IDireccion {
    return {
      ...new Direccion(),
      id: this.editForm.get(['id']).value,
      calle: this.editForm.get(['calle']).value,
      numeroint: this.editForm.get(['numeroint']).value,
      numeroext: this.editForm.get(['numeroext']).value,
      colonia: this.editForm.get(['colonia']).value,
      ciudad: this.editForm.get(['ciudad']).value,
      municipio: this.editForm.get(['municipio']).value,
      estado: this.editForm.get(['estado']).value,
      referencia: this.editForm.get(['referencia']).value,
      entrecalle: this.editForm.get(['entrecalle']).value,
      persona: this.editForm.get(['persona']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDireccion>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }
}
