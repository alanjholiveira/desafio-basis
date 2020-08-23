package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.servico.mapper.ProfessorDetalhadoMapper;
import br.com.basis.prova.servico.mapper.ProfessorListagemMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfessorServico {

    private ProfessorRepositorio professorRepositorio;
    private ProfessorMapper professorMapper;
    private ProfessorListagemMapper professorListagemMapper;
    private ProfessorDetalhadoMapper professorDetalhadoMapper;

    @Autowired
    private DisciplinaRepositorio disciplinaRepositorio;

    @Autowired
    private DisciplinaListagemMapper disciplinaListagemMapper;

    public ProfessorServico(ProfessorMapper professorMapper, ProfessorRepositorio professorRepositorio,
                            ProfessorListagemMapper professorListagemMapper,
                            ProfessorDetalhadoMapper professorDetalhadoMapper) {
        this.professorMapper = professorMapper;
        this.professorRepositorio = professorRepositorio;
        this.professorListagemMapper = professorListagemMapper;
        this.professorDetalhadoMapper = professorDetalhadoMapper;
    }

    public ProfessorDTO salvar(ProfessorDTO professorDTO) {
        Professor professor = professorMapper.toEntity(professorDTO);

        professorRepositorio.save(professor);

        return professorMapper.toDto(professor);
    }

    public void excluir(String matricula) {
        Optional<Professor> professor = professorRepositorio.findByMatricula(matricula);

        if(professor.isPresent() && verificarProfessorDisciplina(professor.get())) {
            professorRepositorio.delete(professor.get());
        } else {
            throw new RegraNegocioException("Não é possivel excluir professor(a) associado(a) à alguma disciplina(s)");
        }

    }

    private boolean verificarProfessorDisciplina(Professor professor) {
        return (professor.getDisciplinas() == null || professor.getDisciplinas().size() == 0);
    }

    public List<ProfessorListagemDTO> consultar() {
        List<Professor> professor = professorRepositorio.findAll();
        return professorListagemMapper.toDto(professor);
    }

    public ProfessorDetalhadoDTO detalhar(Integer id) {
        Optional<Professor> professor = professorRepositorio.findById(id);

        if(!professor.isPresent()) {
            throw new RegraNegocioException("Professor não localizado");
        }

        List<Disciplina> disciplinas = this.disciplinaRepositorio.findDisciplinasAtivaByProfessor(professor.get());
        ProfessorDetalhadoDTO professorDetalhadoDTO = professorDetalhadoMapper.toDto(professor.get());
        professorDetalhadoDTO.setDisciplinas(this.disciplinaListagemMapper.toDto(disciplinas));

        return professorDetalhadoDTO;
    }

    public ProfessorDTO find(Integer id) {
        Professor professor = professorRepositorio.findById(id)
                            .orElseThrow(() -> new RegraNegocioException("Professor não encontrado"));

        return professorMapper.toDto(professor);
    }

}
