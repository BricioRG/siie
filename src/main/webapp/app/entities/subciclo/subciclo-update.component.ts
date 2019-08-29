import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISubciclo, Subciclo } from 'app/shared/model/subciclo.model';
import { SubcicloService } from './subciclo.service';
import { ICiclo } from 'app/shared/model/ciclo.model';
import { CicloService } from 'app/entities/ciclo';
import { IEscuela } from 'app/shared/model/escuela.model';
import { EscuelaService } from 'app/entities/escuela';
import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';
import { TipoperiodoService } from 'app/entities/tipoperiodo';

@Component({
  selector: 'jhi-subciclo-update',
  templateUrl: './subciclo-update.component.html'
})
export class SubcicloUpdateComponent implements OnInit {
  isSaving: boolean;

  ciclos: ICiclo[];

  escuelas: IEscuela[];

  tipoperiodos: ITipoperiodo[];
  feciniDp: any;
  fechafinDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    fecini: [],
    fechafin: [],
    ciclo: [],
    escuela: [],
    tipoperiodo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected subcicloService: SubcicloService,
    protected cicloService: CicloService,
    protected escuelaService: EscuelaService,
    protected tipoperiodoService: TipoperiodoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subciclo }) => {
      this.updateForm(subciclo);
    });
    this.cicloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICiclo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICiclo[]>) => response.body)
      )
      .subscribe((res: ICiclo[]) => (this.ciclos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.escuelaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEscuela[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEscuela[]>) => response.body)
      )
      .subscribe((res: IEscuela[]) => (this.escuelas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoperiodoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoperiodo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoperiodo[]>) => response.body)
      )
      .subscribe((res: ITipoperiodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(subciclo: ISubciclo) {
    this.editForm.patchValue({
      id: subciclo.id,
      nombre: subciclo.nombre,
      descripcion: subciclo.descripcion,
      fecini: subciclo.fecini,
      fechafin: subciclo.fechafin,
      ciclo: subciclo.ciclo,
      escuela: subciclo.escuela,
      tipoperiodo: subciclo.tipoperiodo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subciclo = this.createFromForm();
    if (subciclo.id !== undefined) {
      this.subscribeToSaveResponse(this.subcicloService.update(subciclo));
    } else {
      this.subscribeToSaveResponse(this.subcicloService.create(subciclo));
    }
  }

  private createFromForm(): ISubciclo {
    return {
      ...new Subciclo(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fecini: this.editForm.get(['fecini']).value,
      fechafin: this.editForm.get(['fechafin']).value,
      ciclo: this.editForm.get(['ciclo']).value,
      escuela: this.editForm.get(['escuela']).value,
      tipoperiodo: this.editForm.get(['tipoperiodo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubciclo>>) {
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

  trackCicloById(index: number, item: ICiclo) {
    return item.id;
  }

  trackEscuelaById(index: number, item: IEscuela) {
    return item.id;
  }

  trackTipoperiodoById(index: number, item: ITipoperiodo) {
    return item.id;
  }
}
