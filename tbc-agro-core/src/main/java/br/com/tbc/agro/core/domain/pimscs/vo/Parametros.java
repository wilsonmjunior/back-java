package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PARAMETROS")
public class Parametros {

    @EmbeddedId
    private ParametrosId id;

    @Column(name = "SESSAO", length = 250)
    private String sessao;

    @Column(name = "VALOR", length = 250)
    private String valor;
}
