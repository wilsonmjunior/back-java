package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TipoDespesaDetalheId implements Serializable {

    @Column(name = "CD_TIPO_DESPESA")
    private Long tipoDespesaId;

    @Column(name = "CD_CONTA")
    private String contaContabilId;

}
