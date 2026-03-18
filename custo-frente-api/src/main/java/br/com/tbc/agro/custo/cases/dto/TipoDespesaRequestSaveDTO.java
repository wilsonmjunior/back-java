package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesa;
import java.util.List;

public record TipoDespesaRequestSaveDTO(Long codigo,
                                        String descricao,
                                        List<String> contas) {

    public TipoDespesa toModel() {
        final var response = new TipoDespesa();

        response.setCodigo(this.codigo);
        response.setDescricao(this.descricao);

        return response;
    }
}


