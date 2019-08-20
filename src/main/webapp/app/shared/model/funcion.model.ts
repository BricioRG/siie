import { IModulo } from 'app/shared/model/modulo.model';
import { IRol } from 'app/shared/model/rol.model';

export interface IFuncion {
  id?: number;
  nombre?: string;
  modulo?: IModulo;
  rols?: IRol[];
}

export class Funcion implements IFuncion {
  constructor(public id?: number, public nombre?: string, public modulo?: IModulo, public rols?: IRol[]) {}
}
