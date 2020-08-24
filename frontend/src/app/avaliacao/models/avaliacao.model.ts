import { Aluno } from 'src/app/aluno/models/aluno.model';
import { Disciplina } from 'src/app/disciplina/models/disciplina.model';

export class Avaliacao {
    id?: number;
    data?: Date;
    nota?: number;
    aluno?: Aluno;
    disciplina?: Disciplina
}