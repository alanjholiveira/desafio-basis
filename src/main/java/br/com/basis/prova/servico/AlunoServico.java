package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoListagemMapper;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoServico {

    private AlunoMapper alunoMapper;
    private AlunoRepositorio alunoRepositorio;
    private AlunoListagemMapper alunoListagemMapper;

    public AlunoServico(AlunoMapper alunoMapper, AlunoRepositorio alunoRepositorio, AlunoListagemMapper alunoListagemMapper) {
        this.alunoMapper = alunoMapper;
        this.alunoRepositorio = alunoRepositorio;
        this.alunoListagemMapper = alunoListagemMapper;
    }

    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);

        if(verificarCPF(aluno)){
            throw new RegraNegocioException("CPF já existe");
        }

        alunoRepositorio.save(aluno);

        return alunoMapper.toDto(aluno);
    }

    private boolean verificarCPF(Aluno aluno) {
        Aluno alunoCpf = alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    public void excluir(String matricula) {
        Optional<Aluno> aluno = alunoRepositorio.findByMatricula(matricula);

        if (!aluno.isPresent() && verificarAlunoMatriculado(matricula)) {
            throw new RegraNegocioException("Não é possivel excluir aluno matriculado");
        }

        alunoRepositorio.delete(aluno.get());
    }

    private boolean verificarAlunoMatriculado(String matricula) {
        Optional<Aluno> alunoMatricula = alunoRepositorio.findByMatricula(matricula);
        return (alunoMatricula.isPresent() && alunoMatricula.get().getDisciplinas().size() > 0);
    }

    public List<AlunoListagemDTO> consultar() {
        List<Aluno> aluno = alunoRepositorio.findAll();
        return alunoListagemMapper.toDto(aluno);
    }

    public AlunoDetalhadoDTO detalhar(Integer id) {
        Aluno aluno = alunoRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Registro não encontrado"));
        return new AlunoDetalhadoDTO();
    }

}
