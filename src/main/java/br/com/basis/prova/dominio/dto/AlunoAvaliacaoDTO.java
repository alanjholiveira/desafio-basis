package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AlunoAvaliacaoDTO {

    private Integer id;
    private String nome;
    private String matricula;
    private List<AvaliacaoListagemDTO> avaliacoes = new ArrayList<>();

}
