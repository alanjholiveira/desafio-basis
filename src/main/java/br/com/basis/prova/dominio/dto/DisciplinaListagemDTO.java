package br.com.basis.prova.dominio.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DisciplinaListagemDTO {

    private Integer id;
    private String nome;
    private Integer cargaHoraria;
    private String descricao;

}
