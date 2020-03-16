package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaListagemMapper extends EntityMapper<DisciplinaListagemDTO, Disciplina> {
}
