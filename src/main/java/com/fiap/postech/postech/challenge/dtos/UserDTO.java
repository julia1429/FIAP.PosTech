package com.fiap.postech.postech.challenge.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UserDTO(

        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        String login,
        String senha,
        Date data_ultima_alteracao,
        @NotNull @Valid AddressDTO endereco) {
}
