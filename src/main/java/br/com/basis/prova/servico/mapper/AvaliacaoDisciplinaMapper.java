package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.AvaliacaoDisciplinaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AvaliacaoMapper.class})
public interface AvaliacaoDisciplinaMapper extends EntityMapper<AvaliacaoDisciplinaDTO, Disciplina> {
}
