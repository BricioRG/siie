import { IFuncion } from 'app/shared/model/funcion.model';

export interface IRol {
  id?: number;
  nombre?: string;
  descripcion?: string;
  activo?: boolean;
  funcions?: IFuncion[];
}

export class Rol implements IRol {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public activo?: boolean,
    public funcions?: IFuncion[]
  ) {
    this.activo = this.activo || false;
  }
}
