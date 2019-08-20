import { IPersona } from 'app/shared/model/persona.model';

export interface IDireccion {
  id?: number;
  calle?: string;
  numeroint?: string;
  numeroext?: string;
  colonia?: string;
  ciudad?: string;
  municipio?: string;
  estado?: string;
  referencia?: string;
  entrecalle?: string;
  persona?: IPersona;
}

export class Direccion implements IDireccion {
  constructor(
    public id?: number,
    public calle?: string,
    public numeroint?: string,
    public numeroext?: string,
    public colonia?: string,
    public ciudad?: string,
    public municipio?: string,
    public estado?: string,
    public referencia?: string,
    public entrecalle?: string,
    public persona?: IPersona
  ) {}
}
