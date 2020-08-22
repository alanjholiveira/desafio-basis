package br.com.basis.prova.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DISCIPLINA")
@Getter
@Setter
@NoArgsConstructor
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "CARGA_HORARIA", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "ATIVA", nullable = false)
    private Integer ativa;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "ID_PROFESSOR", referencedColumnName = "ID")
    private Professor professor;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Aluno> aluno;

}
