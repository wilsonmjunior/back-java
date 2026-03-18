package br.com.tbc.agro.core.domain.pimscs.vo;

public enum TipoProcessoEnum {
    C("CUSTO"),
    O("ORÇAMENTO");

    private String descricao;

    TipoProcessoEnum(final String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
