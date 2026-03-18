package br.com.tbc.agro.core.domain.pimscs.vo;

import lombok.Getter;

@Getter
public enum FgTpRecursoEnum {
    E("Equipamento"),
    M("Mão de Obra");

    private String descricao;

    FgTpRecursoEnum(final String descricao) {
        this.descricao = descricao;
    }

}
