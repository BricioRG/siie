import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRol, Rol } from 'app/shared/model/rol.model';
import { RolService } from './rol.service';
import { IFuncion } from 'app/shared/model/funcion.model';
import { FuncionService } from 'app/entities/funcion';

@Component({
  selector: 'jhi-rol-update',
  templateUrl: './rol-update.component.html'
})
export class RolUpdateComponent implements OnInit {
  isSaving: boolean;

  funcions: IFuncion[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    activo: [],
    funcions: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected rolService: RolService,
    protected funcionService: FuncionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rol }) => {
      this.updateForm(rol);
    });
    this.funcionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncion[]>) => response.body)
      )
      .subscribe((res: IFuncion[]) => (this.funcions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rol: IRol) {
    this.editForm.patchValue({
      id: rol.id,
      nombre: rol.nombre,
      descripcion: rol.descripcion,
      activo: rol.activo,
      funcions: rol.funcions
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rol = this.createFromForm();
    if (rol.id !== undefined) {
      this.subscribeToSaveResponse(this.rolService.update(rol));
    } else {
      this.subscribeToSaveResponse(this.rolService.create(rol));
    }
  }

  private createFromForm(): IRol {
    return {
      ...new Rol(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      activo: this.editForm.get(['activo']).value,
      funcions: this.editForm.get(['funcions']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRol>>) {
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

  trackFuncionById(index: number, item: IFuncion) {
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
