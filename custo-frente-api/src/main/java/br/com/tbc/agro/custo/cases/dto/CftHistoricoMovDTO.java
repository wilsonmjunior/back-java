package br.com.tbc.agro.custo.cases.dto;

public record CftHistoricoMovDTO(
        Long cdEquipto,
        Long cdFrenTrab,
        Long cdTipoDespesa,
        Double qtTonelada,
        Double qtHrKm,
        Double vlDespesa
) {
}