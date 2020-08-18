package br.com.basis.prova.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PROFESSOR")
@Getter
@Setter
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "MATRICULA", nullable = false)
    private String matricula;

    @Column(name = "AREA_ATUACAO", nullable = false)
    private String area;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
}
