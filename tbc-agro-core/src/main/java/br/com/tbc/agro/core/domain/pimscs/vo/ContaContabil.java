package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CTA_CSTO")
public class ContaContabil {

    @Id
    @Column(name = "CD_CONTA")
    private String codigo;

    @Column(name = "DE_CONTA")
    private String descricao;

    @Column(name = "DA_CONTA")
    private String descricaoEstendida;

}
