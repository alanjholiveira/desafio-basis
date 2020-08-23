package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoAvaliacaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AvaliacaoListagemMapper.class})
public interface AlunoAvaliacaoMapper extends EntityMapper<AlunoAvaliacaoDTO, Aluno> {
}
