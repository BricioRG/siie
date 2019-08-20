import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEscolaridad, Escolaridad } from 'app/shared/model/escolaridad.model';
import { EscolaridadService } from './escolaridad.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona';

@Component({
  selector: 'jhi-escolaridad-update',
  templateUrl: './escolaridad-update.component.html'
})
export class EscolaridadUpdateComponent implements OnInit {
  isSaving: boolean;

  personas: IPersona[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    periodo: [],
    concluyo: [],
    clavedoc: [],
    documento: [],
    persona: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected escolaridadService: EscolaridadService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ escolaridad }) => {
      this.updateForm(escolaridad);
    });
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(escolaridad: IEscolaridad) {
    this.editForm.patchValue({
      id: escolaridad.id,
      nombre: escolaridad.nombre,
      descripcion: escolaridad.descripcion,
      periodo: escolaridad.periodo,
      concluyo: escolaridad.concluyo,
      clavedoc: escolaridad.clavedoc,
      documento: escolaridad.documento,
      persona: escolaridad.persona
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const escolaridad = this.createFromForm();
    if (escolaridad.id !== undefined) {
      this.subscribeToSaveResponse(this.escolaridadService.update(escolaridad));
    } else {
      this.subscribeToSaveResponse(this.escolaridadService.create(escolaridad));
    }
  }

  private createFromForm(): IEscolaridad {
    return {
      ...new Escolaridad(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      periodo: this.editForm.get(['periodo']).value,
      concluyo: this.editForm.get(['concluyo']).value,
      clavedoc: this.editForm.get(['clavedoc']).value,
      documento: this.editForm.get(['documento']).value,
      persona: this.editForm.get(['persona']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscolaridad>>) {
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
