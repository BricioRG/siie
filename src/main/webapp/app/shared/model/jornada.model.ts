export interface IJornada {
  id?: number;
  clave?: string;
  descripcion?: string;
}

export class Jornada implements IJornada {
  constructor(public id?: number, public clave?: string, public descripcion?: string) {}
}
