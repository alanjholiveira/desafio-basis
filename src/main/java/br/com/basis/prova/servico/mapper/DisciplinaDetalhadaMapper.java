package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DisciplinaListagemMapper.class})
public interface DisciplinaDetalhadaMapper extends EntityMapper<DisciplinaDetalhadaDTO, Disciplina> {
}
