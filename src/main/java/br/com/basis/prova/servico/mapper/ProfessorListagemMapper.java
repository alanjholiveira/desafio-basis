package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfessorMapper.class})
public interface ProfessorListagemMapper extends EntityMapper<ProfessorListagemDTO, Professor> {
}
