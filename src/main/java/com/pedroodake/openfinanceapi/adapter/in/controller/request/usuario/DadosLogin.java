package com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosLogin(
        @NotBlank
        @Email
        String login,

        @NotBlank
        @Pattern(regexp = "^(?=.*\\d).{8,}$", message = "A senha deve ter pelo menos 8 caracteres e conter um número.")
        String senha) {
}