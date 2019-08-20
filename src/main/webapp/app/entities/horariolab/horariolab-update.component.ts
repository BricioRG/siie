import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IHorariolab, Horariolab } from 'app/shared/model/horariolab.model';
import { HorariolabService } from './horariolab.service';
import { IPlaza } from 'app/shared/model/plaza.model';
import { PlazaService } from 'app/entities/plaza';
import { ISubciclo } from 'app/shared/model/subciclo.model';
import { SubcicloService } from 'app/entities/subciclo';

@Component({
  selector: 'jhi-horariolab-update',
  templateUrl: './horariolab-update.component.html'
})
export class HorariolabUpdateComponent implements OnInit {
  isSaving: boolean;

  plazas: IPlaza[];

  subciclos: ISubciclo[];

  editForm = this.fb.group({
    id: [],
    dia: [],
    hinicio: [],
    hfin: [],
    plaza: [],
    subsiclo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected horariolabService: HorariolabService,
    protected plazaService: PlazaService,
    protected subcicloService: SubcicloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ horariolab }) => {
      this.updateForm(horariolab);
    });
    this.plazaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlaza[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlaza[]>) => response.body)
      )
      .subscribe((res: IPlaza[]) => (this.plazas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.subcicloService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISubciclo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISubciclo[]>) => response.body)
      )
      .subscribe((res: ISubciclo[]) => (this.subciclos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(horariolab: IHorariolab) {
    this.editForm.patchValue({
      id: horariolab.id,
      dia: horariolab.dia,
      hinicio: horariolab.hinicio,
      hfin: horariolab.hfin,
      plaza: horariolab.plaza,
      subsiclo: horariolab.subsiclo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const horariolab = this.createFromForm();
    if (horariolab.id !== undefined) {
      this.subscribeToSaveResponse(this.horariolabService.update(horariolab));
    } else {
      this.subscribeToSaveResponse(this.horariolabService.create(horariolab));
    }
  }

  private createFromForm(): IHorariolab {
    return {
      ...new Horariolab(),
      id: this.editForm.get(['id']).value,
      dia: this.editForm.get(['dia']).value,
      hinicio: this.editForm.get(['hinicio']).value,
      hfin: this.editForm.get(['hfin']).value,
      plaza: this.editForm.get(['plaza']).value,
      subsiclo: this.editForm.get(['subsiclo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorariolab>>) {
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

  trackPlazaById(index: number, item: IPlaza) {
    return item.id;
  }

  trackSubcicloById(index: number, item: ISubciclo) {
    return item.id;
  }
}
