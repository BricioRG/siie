import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICategoria, Categoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';
import { IJornada } from 'app/shared/model/jornada.model';
import { JornadaService } from 'app/entities/jornada';
import { IPartida } from 'app/shared/model/partida.model';
import { PartidaService } from 'app/entities/partida';

@Component({
  selector: 'jhi-categoria-update',
  templateUrl: './categoria-update.component.html'
})
export class CategoriaUpdateComponent implements OnInit {
  isSaving: boolean;

  jornadas: IJornada[];

  partidas: IPartida[];

  editForm = this.fb.group({
    id: [],
    tipocat: [],
    clave: [],
    descripcion: [],
    jornada: [],
    partida: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected categoriaService: CategoriaService,
    protected jornadaService: JornadaService,
    protected partidaService: PartidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categoria }) => {
      this.updateForm(categoria);
    });
    this.jornadaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IJornada[]>) => mayBeOk.ok),
        map((response: HttpResponse<IJornada[]>) => response.body)
      )
      .subscribe((res: IJornada[]) => (this.jornadas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.partidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPartida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPartida[]>) => response.body)
      )
      .subscribe((res: IPartida[]) => (this.partidas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(categoria: ICategoria) {
    this.editForm.patchValue({
      id: categoria.id,
      tipocat: categoria.tipocat,
      clave: categoria.clave,
      descripcion: categoria.descripcion,
      jornada: categoria.jornada,
      partida: categoria.partida
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categoria = this.createFromForm();
    if (categoria.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaService.update(categoria));
    } else {
      this.subscribeToSaveResponse(this.categoriaService.create(categoria));
    }
  }

  private createFromForm(): ICategoria {
    return {
      ...new Categoria(),
      id: this.editForm.get(['id']).value,
      tipocat: this.editForm.get(['tipocat']).value,
      clave: this.editForm.get(['clave']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      jornada: this.editForm.get(['jornada']).value,
      partida: this.editForm.get(['partida']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>) {
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

  trackJornadaById(index: number, item: IJornada) {
    return item.id;
  }

  trackPartidaById(index: number, item: IPartida) {
    return item.id;
  }
}
