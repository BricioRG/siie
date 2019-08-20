import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFuncion, Funcion } from 'app/shared/model/funcion.model';
import { FuncionService } from './funcion.service';
import { IModulo } from 'app/shared/model/modulo.model';
import { ModuloService } from 'app/entities/modulo';
import { IRol } from 'app/shared/model/rol.model';
import { RolService } from 'app/entities/rol';

@Component({
  selector: 'jhi-funcion-update',
  templateUrl: './funcion-update.component.html'
})
export class FuncionUpdateComponent implements OnInit {
  isSaving: boolean;

  modulos: IModulo[];

  rols: IRol[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    modulo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected funcionService: FuncionService,
    protected moduloService: ModuloService,
    protected rolService: RolService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ funcion }) => {
      this.updateForm(funcion);
    });
    this.moduloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulo[]>) => response.body)
      )
      .subscribe((res: IModulo[]) => (this.modulos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.rolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRol[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRol[]>) => response.body)
      )
      .subscribe((res: IRol[]) => (this.rols = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(funcion: IFuncion) {
    this.editForm.patchValue({
      id: funcion.id,
      nombre: funcion.nombre,
      modulo: funcion.modulo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const funcion = this.createFromForm();
    if (funcion.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionService.update(funcion));
    } else {
      this.subscribeToSaveResponse(this.funcionService.create(funcion));
    }
  }

  private createFromForm(): IFuncion {
    return {
      ...new Funcion(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      modulo: this.editForm.get(['modulo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncion>>) {
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

  trackModuloById(index: number, item: IModulo) {
    return item.id;
  }

  trackRolById(index: number, item: IRol) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
