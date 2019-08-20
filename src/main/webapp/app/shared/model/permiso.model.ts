import { IRol } from 'app/shared/model/rol.model';
import { IFuncion } from 'app/shared/model/funcion.model';

export interface IPermiso {
  id?: number;
  rol?: IRol;
  funcion?: IFuncion;
}

export class Permiso implements IPermiso {
  constructor(public id?: number, public rol?: IRol, public funcion?: IFuncion) {}
}
