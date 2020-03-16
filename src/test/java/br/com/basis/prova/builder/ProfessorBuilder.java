package br.com.basis.prova.builder;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.DisciplinaServico;
import br.com.basis.prova.servico.ProfessorServico;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class ProfessorBuilder extends ConstrutorDeEntidade<Professor> {

    @Autowired
    private ProfessorServico professorServico;

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private ProfessorRepositorio professorRepositorio;


    @Override
    public Professor construirEntidade() throws ParseException {

        Professor professor = new Professor();

        professor.setNome("Teobaldo");
        professor.setMatricula("123456");
        professor.setArea("Area de teste");
        professor.setDataNascimento(LocalDate.of(1988, 10, 10));

        return professor;
    }

    @Override
    protected Professor persistir(Professor professor) {
        ProfessorDTO professorDTO = professorMapper.toDto(professor);
        return professorMapper.toEntity(professorServico.salvar(professorDTO));
    }

    @Override
    public Collection<Professor> obterTodos() {
        return professorRepositorio.findAll();
    }

    @Override
    protected Professor obterPorId(Integer id) {
        return professorRepositorio.findById(id).orElse(null);
    }

    public void excluirPorId(Integer id) {
        professorRepositorio.deleteById(id);
    }
}
