package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
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

        if (!professor.isPresent() && verificarProfessorDisciplina(matricula)) {
            throw new RegraNegocioException("Não é possivel excluir professor o mesmo está associado à alguma disciplina");
        }

        professorRepositorio.delete(professor.get());
    }

    private boolean verificarProfessorDisciplina(String matricula) {
        Optional<Professor> professor = professorRepositorio.findByMatricula(matricula);
        return (professor.isPresent() && professor.get().getDisciplinas().size() > 0);
    }

    public List<ProfessorListagemDTO> consultar() {
        List<Professor> professor = professorRepositorio.findAll();
        return professorListagemMapper.toDto(professor);
    }

    public ProfessorDetalhadoDTO detalhar(Integer id) {
        Professor professor = professorRepositorio
                .findById(id)
                .orElseThrow(() -> new RegraNegocioException("Professor não encontrado"));
        return professorDetalhadoMapper.toDto(professor);
    }

    public ProfessorDTO find(Integer id) {
        Professor professor = professorRepositorio.findById(id)
                            .orElseThrow(() -> new RegraNegocioException("Professor não encontrado"));

        return professorMapper.toDto(professor);
    }

}
