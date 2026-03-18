package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CFT_TIPO_DESPESA_DE")
public class TipoDespesaDetalhe {

    @EmbeddedId
    private TipoDespesaDetalheId id;

    @ManyToOne(optional = false)
    @MapsId("tipoDespesaId")
    @JoinColumn(name = "CD_TIPO_DESPESA", nullable = false)
    private TipoDespesa tipoDespesa;

    @ManyToOne(optional = false)
    @MapsId("contaContabilId")
    @JoinColumn(name = "CD_CONTA", nullable = false)
    private ContaContabil contaContabil;

}
