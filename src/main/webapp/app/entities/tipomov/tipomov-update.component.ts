import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITipomov, Tipomov } from 'app/shared/model/tipomov.model';
import { TipomovService } from './tipomov.service';
import { IMotivomov } from 'app/shared/model/motivomov.model';
import { MotivomovService } from 'app/entities/motivomov';

@Component({
  selector: 'jhi-tipomov-update',
  templateUrl: './tipomov-update.component.html'
})
export class TipomovUpdateComponent implements OnInit {
  isSaving: boolean;

  motivomovs: IMotivomov[];

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    motivomov: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tipomovService: TipomovService,
    protected motivomovService: MotivomovService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipomov }) => {
      this.updateForm(tipomov);
    });
    this.motivomovService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMotivomov[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMotivomov[]>) => response.body)
      )
      .subscribe((res: IMotivomov[]) => (this.motivomovs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tipomov: ITipomov) {
    this.editForm.patchValue({
      id: tipomov.id,
      descripcion: tipomov.descripcion,
      motivomov: tipomov.motivomov
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipomov = this.createFromForm();
    if (tipomov.id !== undefined) {
      this.subscribeToSaveResponse(this.tipomovService.update(tipomov));
    } else {
      this.subscribeToSaveResponse(this.tipomovService.create(tipomov));
    }
  }

  private createFromForm(): ITipomov {
    return {
      ...new Tipomov(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      motivomov: this.editForm.get(['motivomov']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipomov>>) {
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

  trackMotivomovById(index: number, item: IMotivomov) {
    return item.id;
  }
}
