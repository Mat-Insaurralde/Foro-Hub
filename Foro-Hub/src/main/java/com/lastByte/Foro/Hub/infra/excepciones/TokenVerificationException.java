package com.lastByte.Foro.Hub.infra.excepciones;

public class TokenVerificationException extends RuntimeException {
    public TokenVerificationException(String message) {
        super(message);
    }
}