export interface IFinanciamiento {
  id?: number;
  clave?: string;
  descripcion?: string;
}

export class Financiamiento implements IFinanciamiento {
  constructor(public id?: number, public clave?: string, public descripcion?: string) {}
}
