import { IRol } from 'app/shared/model/rol.model';
import { IPersona } from 'app/shared/model/persona.model';

export interface IUsuario {
  id?: number;
  username?: string;
  rol?: IRol;
  persona?: IPersona;
}

export class Usuario implements IUsuario {
  constructor(public id?: number, public username?: string, public rol?: IRol, public persona?: IPersona) {}
}
