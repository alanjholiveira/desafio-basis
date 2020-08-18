package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoListagemMapper extends EntityMapper<AlunoListagemDTO, Aluno> {
}
