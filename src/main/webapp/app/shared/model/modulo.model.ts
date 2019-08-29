import { IModulo } from 'app/shared/model/modulo.model';

export interface IModulo {
  id?: number;
  nombre?: string;
  padre?: IModulo;
}

export class Modulo implements IModulo {
  constructor(public id?: number, public nombre?: string, public padre?: IModulo) {}
}
