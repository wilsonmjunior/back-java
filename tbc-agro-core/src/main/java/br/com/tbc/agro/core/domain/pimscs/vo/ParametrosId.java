package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class ParametrosId implements Serializable {

    @Column(name = "INSTANCIA")
    private String instancia;

    @Column(name = "SECAO")
    private String secao;

    @Column(name = "ENTRADA")
    private String entrada;
}
