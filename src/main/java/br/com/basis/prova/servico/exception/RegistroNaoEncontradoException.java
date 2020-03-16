package br.com.basis.prova.servico.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Erro de negócio.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException(final String message) {
        this(message, null);
    }

    public RegistroNaoEncontradoException(final String message, final Throwable cause) {
        super(message, cause);
    }


}
