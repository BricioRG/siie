import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPartida, Partida } from 'app/shared/model/partida.model';
import { PartidaService } from './partida.service';
import { IFinanciamiento } from 'app/shared/model/financiamiento.model';
import { FinanciamientoService } from 'app/entities/financiamiento';

@Component({
  selector: 'jhi-partida-update',
  templateUrl: './partida-update.component.html'
})
export class PartidaUpdateComponent implements OnInit {
  isSaving: boolean;

  financiamientos: IFinanciamiento[];

  editForm = this.fb.group({
    id: [],
    clave: [],
    nombre: [],
    financiamiento: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected partidaService: PartidaService,
    protected financiamientoService: FinanciamientoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ partida }) => {
      this.updateForm(partida);
    });
    this.financiamientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFinanciamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFinanciamiento[]>) => response.body)
      )
      .subscribe((res: IFinanciamiento[]) => (this.financiamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(partida: IPartida) {
    this.editForm.patchValue({
      id: partida.id,
      clave: partida.clave,
      nombre: partida.nombre,
      financiamiento: partida.financiamiento
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const partida = this.createFromForm();
    if (partida.id !== undefined) {
      this.subscribeToSaveResponse(this.partidaService.update(partida));
    } else {
      this.subscribeToSaveResponse(this.partidaService.create(partida));
    }
  }

  private createFromForm(): IPartida {
    return {
      ...new Partida(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      nombre: this.editForm.get(['nombre']).value,
      financiamiento: this.editForm.get(['financiamiento']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartida>>) {
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

  trackFinanciamientoById(index: number, item: IFinanciamiento) {
    return item.id;
  }
}
