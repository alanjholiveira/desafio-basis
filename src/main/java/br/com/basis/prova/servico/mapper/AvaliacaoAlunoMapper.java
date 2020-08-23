package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AvaliacaoAlunoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvaliacaoAlunoMapper extends EntityMapper<AvaliacaoAlunoDTO, Aluno> {
}
