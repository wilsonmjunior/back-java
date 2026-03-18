package br.com.tbc.agro.core.domain.dbs.dto;

import java.math.BigDecimal;

public record ProcMovEquipamentosDTO(
        String cdEquipto,
        Long cdFrenTrab,
        Long cdTipoDespesa,
        BigDecimal qtTonelada,
        BigDecimal qtHrKm,
        BigDecimal vlDespesa
) { }
