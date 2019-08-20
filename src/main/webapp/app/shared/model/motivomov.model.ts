export interface IMotivomov {
  id?: number;
  clave?: string;
  descripcion?: string;
}

export class Motivomov implements IMotivomov {
  constructor(public id?: number, public clave?: string, public descripcion?: string) {}
}
