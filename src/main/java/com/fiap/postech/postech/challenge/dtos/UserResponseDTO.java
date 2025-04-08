package com.fiap.postech.postech.challenge.dtos;

import com.fiap.postech.postech.challenge.repositories.entities.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserResponseDTO(
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        @Email
        String email,
        Address endereco) {
}
