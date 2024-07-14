package com.lastByte.Foro.Hub.infra.excepciones;

public class CollectionEmptyException extends RuntimeException {
    public CollectionEmptyException(String message) {
        super(message);
    }
}
