package me.josevasquez.techstoreauthspring.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}