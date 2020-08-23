package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoDTO {

    private Integer id;
    private AvaliacaoAlunoDTO aluno;
    private AvaliacaoDisciplinaDTO disciplina;
    private Double nota;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data;

}
