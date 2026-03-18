package br.com.tbc.agro.custo.cases.dto;


import java.time.LocalDate;

public record CftProcessoRequestUpdateDTO(
        LocalDate dtIniProcesso,
        LocalDate novaDtFimProcesso
) {
}