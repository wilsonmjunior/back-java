package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesa;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalhe;
import java.util.ArrayList;
import java.util.List;

public record TipoDespesaDTO(
        Long codigo,
        String descricao,
        List<ContaContabilDTO> contas
) {

    public static TipoDespesaDTO from(final TipoDespesa tipoDespesa) {
        final var contas = new ArrayList<ContaContabilDTO>();

        if (tipoDespesa.getContas() != null) {
            for (TipoDespesaDetalhe detalhe : tipoDespesa.getContas()) {
                contas.add(ContaContabilDTO.from(detalhe));
            }
        }

        return new TipoDespesaDTO(tipoDespesa.getCodigo(), tipoDespesa.getDescricao(), contas);

    }
}
