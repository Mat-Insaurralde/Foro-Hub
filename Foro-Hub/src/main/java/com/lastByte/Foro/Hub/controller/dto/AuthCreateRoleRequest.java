package com.lastByte.Foro.Hub.controller.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(
        @Size(max = 3 , message = "Un usuario solo puede tener 3 roles")
        List<String> roleList
) {
}
