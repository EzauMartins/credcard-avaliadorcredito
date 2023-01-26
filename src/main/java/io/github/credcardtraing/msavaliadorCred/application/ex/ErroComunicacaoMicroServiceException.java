package io.github.credcardtraing.msavaliadorCred.application.ex;

import lombok.Getter;

public class ErroComunicacaoMicroServiceException extends Exception{
    @Getter
    private Integer status;
    public ErroComunicacaoMicroServiceException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
