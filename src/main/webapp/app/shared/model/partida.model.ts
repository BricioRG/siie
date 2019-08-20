import { IFinanciamiento } from 'app/shared/model/financiamiento.model';

export interface IPartida {
  id?: number;
  clave?: string;
  nombre?: string;
  financiamiento?: IFinanciamiento;
}

export class Partida implements IPartida {
  constructor(public id?: number, public clave?: string, public nombre?: string, public financiamiento?: IFinanciamiento) {}
}
