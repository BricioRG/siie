import { IJornada } from 'app/shared/model/jornada.model';
import { IPartida } from 'app/shared/model/partida.model';

export interface ICategoria {
  id?: number;
  tipocat?: string;
  clave?: string;
  descripcion?: string;
  jornada?: IJornada;
  partida?: IPartida;
}

export class Categoria implements ICategoria {
  constructor(
    public id?: number,
    public tipocat?: string,
    public clave?: string,
    public descripcion?: string,
    public jornada?: IJornada,
    public partida?: IPartida
  ) {}
}
