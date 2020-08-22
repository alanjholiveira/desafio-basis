package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.util.Helpers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AlunoListagemDTO { // DTO usado para consulta simples de alunos

    private Integer id;
    private String nome;
    private String matricula;
    @JsonIgnore
    private LocalDate dataNascimento;
    public Integer getIdade() {
        return Helpers.calcularIdadeByDataNascimento(this.dataNascimento);
    }

}
