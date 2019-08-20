import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModulo, Modulo } from 'app/shared/model/modulo.model';
import { ModuloService } from './modulo.service';

@Component({
  selector: 'jhi-modulo-update',
  templateUrl: './modulo-update.component.html'
})
export class ModuloUpdateComponent implements OnInit {
  isSaving: boolean;

  modulos: IModulo[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    padre: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected moduloService: ModuloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ modulo }) => {
      this.updateForm(modulo);
    });
    this.moduloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModulo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModulo[]>) => response.body)
      )
      .subscribe((res: IModulo[]) => (this.modulos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(modulo: IModulo) {
    this.editForm.patchValue({
      id: modulo.id,
      nombre: modulo.nombre,
      padre: modulo.padre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const modulo = this.createFromForm();
    if (modulo.id !== undefined) {
      this.subscribeToSaveResponse(this.moduloService.update(modulo));
    } else {
      this.subscribeToSaveResponse(this.moduloService.create(modulo));
    }
  }

  private createFromForm(): IModulo {
    return {
      ...new Modulo(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      padre: this.editForm.get(['padre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModulo>>) {
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
}
