package br.com.basis.prova.dominio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer cargaHoraria;
    private Integer ativa;

    private String idProfessor;

}
