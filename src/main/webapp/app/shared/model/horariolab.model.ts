import { IPlaza } from 'app/shared/model/plaza.model';
import { ISubciclo } from 'app/shared/model/subciclo.model';

export interface IHorariolab {
  id?: number;
  dia?: string;
  hinicio?: string;
  hfin?: string;
  plaza?: IPlaza;
  subsiclo?: ISubciclo;
}

export class Horariolab implements IHorariolab {
  constructor(
    public id?: number,
    public dia?: string,
    public hinicio?: string,
    public hfin?: string,
    public plaza?: IPlaza,
    public subsiclo?: ISubciclo
  ) {}
}
