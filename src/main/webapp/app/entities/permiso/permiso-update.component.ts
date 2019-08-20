import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPermiso, Permiso } from 'app/shared/model/permiso.model';
import { PermisoService } from './permiso.service';
import { IRol } from 'app/shared/model/rol.model';
import { RolService } from 'app/entities/rol';
import { IFuncion } from 'app/shared/model/funcion.model';
import { FuncionService } from 'app/entities/funcion';

@Component({
  selector: 'jhi-permiso-update',
  templateUrl: './permiso-update.component.html'
})
export class PermisoUpdateComponent implements OnInit {
  isSaving: boolean;

  rols: IRol[];

  funcions: IFuncion[];

  editForm = this.fb.group({
    id: [],
    rol: [],
    funcion: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected permisoService: PermisoService,
    protected rolService: RolService,
    protected funcionService: FuncionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ permiso }) => {
      this.updateForm(permiso);
    });
    this.rolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRol[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRol[]>) => response.body)
      )
      .subscribe((res: IRol[]) => (this.rols = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.funcionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncion[]>) => response.body)
      )
      .subscribe((res: IFuncion[]) => (this.funcions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(permiso: IPermiso) {
    this.editForm.patchValue({
      id: permiso.id,
      rol: permiso.rol,
      funcion: permiso.funcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const permiso = this.createFromForm();
    if (permiso.id !== undefined) {
      this.subscribeToSaveResponse(this.permisoService.update(permiso));
    } else {
      this.subscribeToSaveResponse(this.permisoService.create(permiso));
    }
  }

  private createFromForm(): IPermiso {
    return {
      ...new Permiso(),
      id: this.editForm.get(['id']).value,
      rol: this.editForm.get(['rol']).value,
      funcion: this.editForm.get(['funcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermiso>>) {
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

  trackRolById(index: number, item: IRol) {
    return item.id;
  }

  trackFuncionById(index: number, item: IFuncion) {
    return item.id;
  }
}
