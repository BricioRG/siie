import { Moment } from 'moment';

export interface ICiclo {
  id?: number;
  clave?: string;
  descripcion?: string;
  fecini?: Moment;
  fecfin?: Moment;
}

export class Ciclo implements ICiclo {
  constructor(public id?: number, public clave?: string, public descripcion?: string, public fecini?: Moment, public fecfin?: Moment) {}
}
