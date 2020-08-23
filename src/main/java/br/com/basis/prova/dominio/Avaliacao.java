package br.com.basis.prova.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AVALIACAO")
@Getter
@Setter
@NoArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "ID_DISCIPLINA", referencedColumnName = "ID")
    private Disciplina disciplina;

    private Double nota;

    private LocalDate data;

}
