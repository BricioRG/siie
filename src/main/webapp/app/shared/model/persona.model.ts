import { Moment } from 'moment';

export interface IPersona {
  id?: number;
  nombre?: string;
  primerape?: string;
  segundoape?: string;
  fechanac?: Moment;
  entidadnac?: string;
  genero?: string;
  rfc?: string;
  curp?: string;
  edocivil?: string;
  empleado?: boolean;
  nuempleado?: string;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public nombre?: string,
    public primerape?: string,
    public segundoape?: string,
    public fechanac?: Moment,
    public entidadnac?: string,
    public genero?: string,
    public rfc?: string,
    public curp?: string,
    public edocivil?: string,
    public empleado?: boolean,
    public nuempleado?: string
  ) {
    this.empleado = this.empleado || false;
  }
}
