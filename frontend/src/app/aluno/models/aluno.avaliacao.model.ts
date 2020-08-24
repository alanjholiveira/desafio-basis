import { AvaliacaoListagem } from '../../avaliacao/models/avaliacao.listagem.model';

export class AlunoAvaliacao {
  id?: number;
  nome?: string;
  matricula?: number;
  avaliacoes ?: AvaliacaoListagem[]
}