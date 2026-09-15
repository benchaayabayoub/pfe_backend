package com.pfe.pfeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackFlatDto {

    private Long packId;
    private String packNom;

    private Long teamId;
    private String teamNom;

    private String ipAdresse;
    private String serverNom;

    private String date; // ou LocalDate
    private Long nbrSent;
}