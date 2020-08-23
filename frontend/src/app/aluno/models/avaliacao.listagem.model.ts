import { Disciplina } from '../../disciplina/models/disciplina.model';

export class AvaliacaoListagem {
  id?: number;
  nota?: number;
  data?: Date;
  disciplinas ?: Disciplina[]
}