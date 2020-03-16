package br.com.basis.prova.builder;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.servico.DisciplinaServico;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;

@Component
public class DisciplinaBuilder extends ConstrutorDeEntidade<Disciplina> {

    @Autowired
    private DisciplinaServico disciplinaServico;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    @Autowired
    private DisciplinaRepositorio disciplinaRepositorio;

    @Autowired
    private ProfessorBuilder professorBuilder;


    @Override
    public Disciplina construirEntidade() throws ParseException {

        Professor professor = professorBuilder.construir();

        Disciplina disciplina = new Disciplina();

        disciplina.setNome("Disciplina Teste");
        disciplina.setDescricao("Descricao Teste");
        disciplina.setCargaHoraria(80);
        disciplina.setAtiva(1);
        disciplina.setProfessor(professor);

        return disciplina;
    }

    @Override
    public Disciplina persistir(Disciplina disciplina) {
        DisciplinaDTO disciplinaDTO = disciplinaMapper.toDto(disciplina);
        return disciplinaMapper.toEntity(disciplinaServico.salvar(disciplinaDTO));
    }

    @Override
    public Collection<Disciplina> obterTodos() {
        return disciplinaRepositorio.findAll();
    }

    @Override
    protected Disciplina obterPorId(Integer id) {
        return disciplinaRepositorio.findById(id).orElse(null);
    }

    public void excluirPorId(Integer id) {
        disciplinaRepositorio.deleteById(id);
    }
}
