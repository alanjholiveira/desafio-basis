package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfessorServico {

    private ProfessorRepositorio professorRepositorio;
    private ProfessorMapper professorMapper;

    @Autowired
    private DisciplinaRepositorio disciplinaRepositorio;

    public ProfessorServico(ProfessorMapper professorMapper, ProfessorRepositorio professorRepositorio) {
        this.professorMapper = professorMapper;
        this.professorRepositorio = professorRepositorio;
    }

    public ProfessorDTO salvar(ProfessorDTO professorDTO) {
        return null;
    }

    public void excluir(String matricula) {
        Professor professor = new Professor();
        List<Disciplina> disciplinas = disciplinaRepositorio.findByProfessor(professor);
    }

    public List<ProfessorDTO> consultar() {
        return new ArrayList<>();
    }

    public ProfessorDetalhadoDTO detalhar(Integer id) {
        return new ProfessorDetalhadoDTO();
    }

}
