export interface IFuncionlaboral {
  id?: number;
  clave?: string;
  descripcion?: string;
}

export class Funcionlaboral implements IFuncionlaboral {
  constructor(public id?: number, public clave?: string, public descripcion?: string) {}
}
