package br.com.tbc.agro.core.domain.pimscs.vo;

import lombok.Getter;

@Getter
public enum FgProcessadoEnum {
    S("SIM"), N("NÃO");

    private String descricao;

    FgProcessadoEnum(final String descricao) {
        this.descricao = descricao;
    }

}
