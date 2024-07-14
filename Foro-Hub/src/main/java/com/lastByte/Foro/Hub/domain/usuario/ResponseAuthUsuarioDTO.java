package com.lastByte.Foro.Hub.domain.usuario;

public record ResponseAuthUsuarioDTO(
         String message,
         String token

) {
    public ResponseAuthUsuarioDTO(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
