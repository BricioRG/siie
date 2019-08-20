import { Moment } from 'moment';
import { ICiclo } from 'app/shared/model/ciclo.model';
import { IEscuela } from 'app/shared/model/escuela.model';
import { ITipoperiodo } from 'app/shared/model/tipoperiodo.model';

export interface ISubciclo {
  id?: number;
  nombre?: string;
  descripcion?: string;
  fecini?: Moment;
  fechafin?: Moment;
  ciclo?: ICiclo;
  escuela?: IEscuela;
  tipoperiodo?: ITipoperiodo;
}

export class Subciclo implements ISubciclo {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public fecini?: Moment,
    public fechafin?: Moment,
    public ciclo?: ICiclo,
    public escuela?: IEscuela,
    public tipoperiodo?: ITipoperiodo
  ) {}
}
