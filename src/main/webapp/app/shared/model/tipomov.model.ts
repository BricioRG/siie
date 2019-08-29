import { IMotivomov } from 'app/shared/model/motivomov.model';

export interface ITipomov {
  id?: number;
  descripcion?: string;
  motivomov?: IMotivomov;
}

export class Tipomov implements ITipomov {
  constructor(public id?: number, public descripcion?: string, public motivomov?: IMotivomov) {}
}
