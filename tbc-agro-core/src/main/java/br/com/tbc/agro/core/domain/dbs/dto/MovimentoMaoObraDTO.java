package br.com.tbc.agro.core.domain.dbs.dto;

import java.math.BigDecimal;

public record MovimentoMaoObraDTO(
        Long cdCcusto,
        Long cdFrenTrab,
        Long cdTipoDespesa,
        BigDecimal vlDespesa
) {
}
