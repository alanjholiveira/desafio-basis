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

    @Query("SELECT d FROM Disciplina d WHERE d.ativa = 1 AND d.professor = :professor")
    List<Disciplina> findDisciplinasAtivaByProfessor(@Param("professor") Professor professor);

}
