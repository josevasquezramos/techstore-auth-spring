package me.josevasquez.techstoreauthspring.dto;

import me.josevasquez.techstoreauthspring.entity.Role;

public record RegisterRequestDTO(
        String name,
        String email,
        String password,
        Role role
) {}