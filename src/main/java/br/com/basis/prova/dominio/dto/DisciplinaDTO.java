package br.com.basis.prova.dominio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer ativa;
    private DisciplinaProfessorDTO professor;

}
