package br.com.basis.prova.dominio.dto;

import br.com.basis.prova.util.Helpers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorListagemDTO {
    private Integer id;
    private String matricula;
    private String nome;
    private String area;
    @JsonIgnore
    private LocalDate dataNascimento;
    public Integer getIdade() {
        return Helpers.calcularIdadeByDataNascimento(this.dataNascimento);
    }
}
