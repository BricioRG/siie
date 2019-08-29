import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPlaza, Plaza } from 'app/shared/model/plaza.model';
import { PlazaService } from './plaza.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';
import { FuncionlaboralService } from 'app/entities/funcionlaboral';
import { IEscuela } from 'app/shared/model/escuela.model';
import { EscuelaService } from 'app/entities/escuela';
import { ITipomov } from 'app/shared/model/tipomov.model';
import { TipomovService } from 'app/entities/tipomov';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona';

@Component({
  selector: 'jhi-plaza-update',
  templateUrl: './plaza-update.component.html'
})
export class PlazaUpdateComponent implements OnInit {
  isSaving: boolean;

  categorias: ICategoria[];

  funcionlaborals: IFuncionlaboral[];

  escuelas: IEscuela[];

  tipomovs: ITipomov[];

  personas: IPersona[];
  fechainiDp: any;
  fechafinDp: any;

  editForm = this.fb.group({
    id: [],
    horas: [],
    fechaini: [],
    fechafin: [],
    categoria: [],
    funcionlaboral: [],
    escuela: [],
    tipomov: [],
    persona: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected plazaService: PlazaService,
    protected categoriaService: CategoriaService,
    protected funcionlaboralService: FuncionlaboralService,
    protected escuelaService: EscuelaService,
    protected tipomovService: TipomovService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ plaza }) => {
      this.updateForm(plaza);
    });
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.funcionlaboralService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncionlaboral[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncionlaboral[]>) => response.body)
      )
      .subscribe((res: IFuncionlaboral[]) => (this.funcionlaborals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.escuelaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEscuela[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEscuela[]>) => response.body)
      )
      .subscribe((res: IEscuela[]) => (this.escuelas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipomovService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipomov[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipomov[]>) => response.body)
      )
      .subscribe((res: ITipomov[]) => (this.tipomovs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(plaza: IPlaza) {
    this.editForm.patchValue({
      id: plaza.id,
      horas: plaza.horas,
      fechaini: plaza.fechaini,
      fechafin: plaza.fechafin,
      categoria: plaza.categoria,
      funcionlaboral: plaza.funcionlaboral,
      escuela: plaza.escuela,
      tipomov: plaza.tipomov,
      persona: plaza.persona
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const plaza = this.createFromForm();
    if (plaza.id !== undefined) {
      this.subscribeToSaveResponse(this.plazaService.update(plaza));
    } else {
      this.subscribeToSaveResponse(this.plazaService.create(plaza));
    }
  }

  private createFromForm(): IPlaza {
    return {
      ...new Plaza(),
      id: this.editForm.get(['id']).value,
      horas: this.editForm.get(['horas']).value,
      fechaini: this.editForm.get(['fechaini']).value,
      fechafin: this.editForm.get(['fechafin']).value,
      categoria: this.editForm.get(['categoria']).value,
      funcionlaboral: this.editForm.get(['funcionlaboral']).value,
      escuela: this.editForm.get(['escuela']).value,
      tipomov: this.editForm.get(['tipomov']).value,
      persona: this.editForm.get(['persona']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaza>>) {
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

  trackCategoriaById(index: number, item: ICategoria) {
    return item.id;
  }

  trackFuncionlaboralById(index: number, item: IFuncionlaboral) {
    return item.id;
  }

  trackEscuelaById(index: number, item: IEscuela) {
    return item.id;
  }

  trackTipomovById(index: number, item: ITipomov) {
    return item.id;
  }

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }
}
