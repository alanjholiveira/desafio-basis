package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DisciplinaListagemMapper.class})
public interface AlunoDetalhadoMapper extends EntityMapper<AlunoDetalhadoDTO, Aluno> {
}
