package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DisciplinaMapper.class})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {
}
