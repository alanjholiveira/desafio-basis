package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaDetalhadaMapper;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisciplinaServico {

    private DisciplinaRepositorio disciplinaRepositorio;
    private DisciplinaMapper disciplinaMapper;
    private DisciplinaDetalhadaMapper disciplinaDetalhadaMapper;

    @Autowired
    private DisciplinaListagemMapper disciplinaListagemMapper;

    @Autowired
    private ProfessorRepositorio professorRepositorio;

    public DisciplinaServico(DisciplinaMapper disciplinaMapper, DisciplinaRepositorio disciplinaRepositorio,
                             DisciplinaDetalhadaMapper disciplinaDetalhadaMapper) {
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaRepositorio = disciplinaRepositorio;
        this.disciplinaDetalhadaMapper = disciplinaDetalhadaMapper;
    }

    public DisciplinaDTO salvar(DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);

        disciplinaRepositorio.save(disciplina);

        return disciplinaMapper.toDto(disciplina);
    }

    public void excluir(Integer id) {
        Disciplina disciplina = disciplinaRepositorio
                                    .findById(id)
                                    .orElseThrow(() -> new RegraNegocioException("Disciplina não encontrada"));

        if(verificarDisciplinaAluno(disciplina) ) {
            throw new RegraNegocioException("Disciplina Com Aluno(s) Matriculado(s)");
        }

        disciplinaRepositorio.delete(disciplina);
    }

    private boolean verificarDisciplinaAluno(Disciplina disciplina){
        return (disciplina.getAluno().size() > 0);
    }

    public List<DisciplinaListagemDTO> consultar() {
        return disciplinaListagemMapper.toDto(disciplinaRepositorio.findAll());
    }

    public DisciplinaDetalhadaDTO detalhar(Integer id) {
        Disciplina disciplina = disciplinaRepositorio
                                    .findById(id)
                                    .orElseThrow(
                                            () -> new RegraNegocioException("Disciplina não encontrada")
                                    );
        return disciplinaDetalhadaMapper.toDto(disciplina);
    }

}
