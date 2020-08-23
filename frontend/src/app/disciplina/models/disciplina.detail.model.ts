import { Professor } from '../../professor/models/professor.model';

export class DisciplinaDetail {
    id?: number;
    nome?: string;
    cargaHoraria?: number;
    professor?: Professor;
}