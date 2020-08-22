package br.com.basis.prova.util;


import br.com.basis.prova.servico.exception.RegraNegocioException;

import java.time.LocalDate;
import java.time.Period;

public class Helpers {

    public static int calcularIdadeByDataNascimento(LocalDate dataNascimento) {
        LocalDate dateNow = LocalDate.now();

        if (dataNascimento == null ) {
            throw new RegraNegocioException("Data informada invalida");
        }

        return Period.between(dataNascimento, dateNow).getYears();
    }
}
