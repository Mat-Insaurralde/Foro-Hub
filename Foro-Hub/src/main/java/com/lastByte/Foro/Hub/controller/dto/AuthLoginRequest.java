package com.lastByte.Foro.Hub.controller.dto;

import jakarta.validation.constraints.NotNull;

public record AuthLoginRequest(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
