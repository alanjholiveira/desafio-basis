package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AlunoListagemDTO { // DTO usado para consulta simples de alunos

    private Integer id;
    private String nome;
    private String matricula;
    @JsonIgnore
    private LocalDate dataNascimento;
    private Integer idade;

}
