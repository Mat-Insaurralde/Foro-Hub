package com.lastByte.Foro.Hub.controller;


public record ResponseStandarDTO<T>(
        String message,
        T response
        ) {
}
