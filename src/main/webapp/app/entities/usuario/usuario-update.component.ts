import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUsuario, Usuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';
import { IRol } from 'app/shared/model/rol.model';
import { RolService } from 'app/entities/rol';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona';

@Component({
  selector: 'jhi-usuario-update',
  templateUrl: './usuario-update.component.html'
})
export class UsuarioUpdateComponent implements OnInit {
  isSaving: boolean;

  rols: IRol[];

  personas: IPersona[];

  editForm = this.fb.group({
    id: [],
    username: [],
    rol: [],
    persona: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected usuarioService: UsuarioService,
    protected rolService: RolService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ usuario }) => {
      this.updateForm(usuario);
    });
    this.rolService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRol[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRol[]>) => response.body)
      )
      .subscribe((res: IRol[]) => (this.rols = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersona[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersona[]>) => response.body)
      )
      .subscribe((res: IPersona[]) => (this.personas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(usuario: IUsuario) {
    this.editForm.patchValue({
      id: usuario.id,
      username: usuario.username,
      rol: usuario.rol,
      persona: usuario.persona
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const usuario = this.createFromForm();
    if (usuario.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioService.update(usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(usuario));
    }
  }

  private createFromForm(): IUsuario {
    return {
      ...new Usuario(),
      id: this.editForm.get(['id']).value,
      username: this.editForm.get(['username']).value,
      rol: this.editForm.get(['rol']).value,
      persona: this.editForm.get(['persona']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>) {
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

  trackPersonaById(index: number, item: IPersona) {
    return item.id;
  }
}
