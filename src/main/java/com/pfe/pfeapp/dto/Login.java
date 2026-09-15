package com.pfe.pfeapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {

    @NotBlank(message = "Le username Telegram est obligatoire")
    private String usernameTelegram;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}