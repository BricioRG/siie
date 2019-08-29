import { IPersona } from 'app/shared/model/persona.model';

export interface IEscolaridad {
  id?: number;
  nombre?: string;
  descripcion?: string;
  periodo?: string;
  concluyo?: boolean;
  clavedoc?: string;
  documento?: string;
  persona?: IPersona;
}

export class Escolaridad implements IEscolaridad {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public periodo?: string,
    public concluyo?: boolean,
    public clavedoc?: string,
    public documento?: string,
    public persona?: IPersona
  ) {
    this.concluyo = this.concluyo || false;
  }
}
