package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.ContaContabil;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalhe;

public record ContaContabilDTO(
        String codigo,
        String descricao,
        String descricaoEstendida
) {

    public static ContaContabilDTO from(final TipoDespesaDetalhe tipoDespesaDetalhe) {
        final var contaContabil = tipoDespesaDetalhe.getContaContabil();
        return new ContaContabilDTO(
                contaContabil.getCodigo(),
                contaContabil.getDescricao(),
                contaContabil.getDescricaoEstendida());
    }

    public static ContaContabilDTO from(final ContaContabil contaContabil) {
        return new ContaContabilDTO(
                contaContabil.getCodigo(),
                contaContabil.getDescricao(),
                contaContabil.getDescricaoEstendida());
    }
}