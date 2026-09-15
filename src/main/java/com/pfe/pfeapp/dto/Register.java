package com.pfe.pfeapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Register {

    @NotBlank(message = "Le nom complet est obligatoire")
    private String nomComplet;

    @NotBlank(message = "Le username Telegram est obligatoire")
    private String usernameTelegram;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}