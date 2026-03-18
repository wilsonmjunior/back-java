package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CFT_TIPO_DESPESA_HE")
public class TipoDespesa {

    @Id
    @Column(name = "CD_TIPO_DESPESA", nullable = false)
    private Long codigo;

    @Column(name = "DE_TIPO_DESPESA")
    private String descricao;

    @OneToMany(mappedBy = "tipoDespesa",
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<TipoDespesaDetalhe> contas;
}
