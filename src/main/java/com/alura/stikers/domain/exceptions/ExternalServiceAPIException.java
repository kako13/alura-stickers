package com.alura.stikers.domain.exceptions;

public class ExternalServiceAPIException extends RuntimeException{

    public ExternalServiceAPIException(String message) {
        super(message);
    }
}
