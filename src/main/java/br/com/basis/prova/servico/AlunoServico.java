package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.dto.AlunoAvaliacaoDTO;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.repositorio.AvaliacaoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoServico {

    private AlunoMapper alunoMapper;
    private AlunoRepositorio alunoRepositorio;
    private AlunoListagemMapper alunoListagemMapper;
    private AlunoDetalhadoMapper alunoDetalhadoMapper;

    @Autowired
    private AvaliacaoRepositorio avaliacaoRepositorio;
    @Autowired
    private AvaliacaoListagemMapper avaliacaoListagemMapper;
    @Autowired
    private AlunoAvaliacaoMapper alunoAvaliacaoMapper;


    public AlunoServico(AlunoMapper alunoMapper, AlunoRepositorio alunoRepositorio,
                        AlunoListagemMapper alunoListagemMapper, AlunoDetalhadoMapper alunoDetalhadoMapper) {
        this.alunoMapper = alunoMapper;
        this.alunoRepositorio = alunoRepositorio;
        this.alunoListagemMapper = alunoListagemMapper;
        this.alunoDetalhadoMapper = alunoDetalhadoMapper;
    }

    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);

        if(verificarCPF(aluno)){
            throw new RegraNegocioException("CPF já existe");
        }

        alunoRepositorio.save(aluno);

        return alunoMapper.toDto(aluno);
    }

    public AlunoDTO update(AlunoDTO alunoDTO) {
        Optional<Aluno> aluno = alunoRepositorio.findById(alunoDTO.getId());

        if (!aluno.isPresent()) {
            throw new RegraNegocioException("Aluno não encontrado");
        }

        alunoRepositorio.save(alunoMapper.toEntity(alunoDTO));

        return alunoMapper.toDto(aluno.get());

    }

    private boolean verificarCPF(Aluno aluno) {
        Aluno alunoCpf = alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    public void excluir(String matricula) {
        Optional<Aluno> aluno = alunoRepositorio.findByMatricula(matricula);

        if(aluno.isPresent() && verificarAlunoMatriculado(aluno.get())) {
            alunoRepositorio.delete(aluno.get());
        } else {
            throw new RegraNegocioException("Não é possivel excluir aluno matriculado");
        }

    }

    private boolean verificarAlunoMatriculado(Aluno aluno) {
        return (aluno.getDisciplinas() == null || aluno.getDisciplinas().size() == 0);
    }

    public List<AlunoListagemDTO> consultar() {
        List<Aluno> aluno = alunoRepositorio.findAll();
        return alunoListagemMapper.toDto(aluno);
    }

    public AlunoDetalhadoDTO detalhar(Integer id) {
        Aluno aluno = alunoRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Aluno não encontrado"));
        return alunoDetalhadoMapper.toDto(aluno);
    }

    public AlunoDTO find(Integer id) {
        Aluno aluno = alunoRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Aluno não encontrado"));

        return alunoMapper.toDto(aluno);
    }

    public AlunoAvaliacaoDTO findByAvaliacao(Integer id) {
        Optional<Aluno> aluno = this.alunoRepositorio.findById(id);

        if (aluno.isPresent()) {
            List<Avaliacao> avaliacoes = this.avaliacaoRepositorio.findByAluno(aluno.get());
            AlunoAvaliacaoDTO alunoAvaliacaoDTO = this.alunoAvaliacaoMapper.toDto(aluno.get());
            alunoAvaliacaoDTO.setAvaliacoes(this.avaliacaoListagemMapper.toDto(avaliacoes));

            return alunoAvaliacaoDTO;
        }

        throw new RegraNegocioException("Falha ao localizar avaliações do aluno(a)");
    }

}
