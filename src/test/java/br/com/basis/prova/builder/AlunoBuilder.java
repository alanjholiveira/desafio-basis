package br.com.basis.prova.builder;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.servico.AlunoServico;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class AlunoBuilder extends ConstrutorDeEntidade<Aluno> {

    @Autowired
    private AlunoServico alunoServico;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private DisciplinaBuilder disciplinaBuilder;


    @Override
    public Aluno construirEntidade() throws ParseException {

        Aluno aluno = new Aluno();

        aluno.setNome("Aluno Teste");
        aluno.setCpf("64472911019");
        aluno.setDataNascimento(LocalDate.now());
        aluno.setMatricula("123432");

        return aluno;
    }

    @Override
    public Aluno persistir(Aluno entidade) {
        AlunoDTO alunoDTO = alunoMapper.toDto(entidade);
        return alunoMapper.toEntity(alunoServico.salvar(alunoDTO));
    }

    @Override
    public Collection<Aluno> obterTodos() {
        return alunoRepositorio.findAll();
    }

    @Override
    public Aluno obterPorId(Integer id) {
        return alunoRepositorio.findById(id).orElse(null);
    }

    public void excluirPorId(Integer id) {
        alunoRepositorio.deleteById(id);
    }

}
