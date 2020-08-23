package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.dto.AvaliacaoDTO;
import br.com.basis.prova.repositorio.AvaliacaoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AvaliacaoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvaliacaoServico {

    private AvaliacaoRepositorio avaliacaoRepositorio;
    private AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoServico(AvaliacaoRepositorio avaliacaoRepositorio, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepositorio = avaliacaoRepositorio;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    public AvaliacaoDTO salvar(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = this.avaliacaoMapper.toEntity(avaliacaoDTO);

        this.avaliacaoRepositorio.save(avaliacao);

        return this.avaliacaoMapper.toDto(avaliacao);
    }

    public AvaliacaoDTO update(AvaliacaoDTO avaliacaoDTO) {
        Optional<Avaliacao> avaliacao = this.avaliacaoRepositorio.findById(avaliacaoDTO.getId());

        if (avaliacao.isPresent()) {
            this.avaliacaoRepositorio.save(this.avaliacaoMapper.toEntity(avaliacaoDTO));
            return this.avaliacaoMapper.toDto(avaliacao.get());
        } else {
            throw new RegraNegocioException("Falha ao atualizar registro");
        }
    }

    public void excluir(Integer id) {
        Optional<Avaliacao> avaliacao = this.avaliacaoRepositorio.findById(id);

        if (avaliacao.isPresent()) {
            this.avaliacaoRepositorio.delete(avaliacao.get());
        } else {
            throw new RegraNegocioException("Falha ao excluir Nota da Avaliação");
        }
    }

    public List<AvaliacaoDTO> consultar() {
        return this.avaliacaoMapper.toDto(this.avaliacaoRepositorio.findAll());
    }


}
