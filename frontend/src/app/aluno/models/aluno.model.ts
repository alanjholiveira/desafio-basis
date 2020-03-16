import { Disciplina } from '../../disciplina/models/disciplina.model';

export class Aluno {
  id?: number;
  nome?: string;
  cpf?: string;
  dataNascimento?: Date;
  idade?: number;
  matricula?: number;
  disciplinas ?: Disciplina[]
}