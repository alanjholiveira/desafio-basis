package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepositorio extends JpaRepository<Disciplina, Integer>, JpaSpecificationExecutor<Disciplina> {

    List<Disciplina> findByProfessor(Professor professor);

    @Query(value = "SELECT D.ID, D.NOME, D.DESCRICAO, D.CARGA_HORARIA, D.ATIVA, D.ID_PROFESSOR\n" +
            "FROM disciplina as D, professor as P WHERE D.ATIVA = 1 AND P.ID = :id\n" +
            "AND D.ID_PROFESSOR = :id", nativeQuery = true)
    List<Disciplina> findByAtivas(@Param("id") Integer id);

}
