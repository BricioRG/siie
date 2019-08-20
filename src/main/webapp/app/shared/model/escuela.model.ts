export interface IEscuela {
  id?: number;
  cct?: string;
  nombre?: string;
}

export class Escuela implements IEscuela {
  constructor(public id?: number, public cct?: string, public nombre?: string) {}
}
