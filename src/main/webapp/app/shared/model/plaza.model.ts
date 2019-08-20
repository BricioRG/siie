import { Moment } from 'moment';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IFuncionlaboral } from 'app/shared/model/funcionlaboral.model';
import { IEscuela } from 'app/shared/model/escuela.model';
import { ITipomov } from 'app/shared/model/tipomov.model';
import { IPersona } from 'app/shared/model/persona.model';

export interface IPlaza {
  id?: number;
  horas?: string;
  fechaini?: Moment;
  fechafin?: Moment;
  categoria?: ICategoria;
  funcionlaboral?: IFuncionlaboral;
  escuela?: IEscuela;
  tipomov?: ITipomov;
  persona?: IPersona;
}

export class Plaza implements IPlaza {
  constructor(
    public id?: number,
    public horas?: string,
    public fechaini?: Moment,
    public fechafin?: Moment,
    public categoria?: ICategoria,
    public funcionlaboral?: IFuncionlaboral,
    public escuela?: IEscuela,
    public tipomov?: ITipomov,
    public persona?: IPersona
  ) {}
}
