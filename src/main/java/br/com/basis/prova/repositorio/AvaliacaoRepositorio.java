package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.Avaliacao;
import br.com.basis.prova.dominio.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepositorio extends JpaRepository<Avaliacao, Integer>, JpaSpecificationExecutor<Avaliacao> {

    List<Avaliacao> findByDisciplina(Disciplina disciplina);

    List<Avaliacao> findByAluno(Aluno aluno);

}
