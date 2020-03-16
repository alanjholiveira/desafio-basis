package br.com.basis.prova.repositorio;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepositorio extends JpaRepository<Disciplina, Integer>, JpaSpecificationExecutor<Disciplina> {

    List<Disciplina> findByProfessor(Professor professor);

}
