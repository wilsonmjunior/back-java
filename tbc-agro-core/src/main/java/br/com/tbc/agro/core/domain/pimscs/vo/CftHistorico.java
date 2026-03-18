package br.com.tbc.agro.core.domain.pimscs.vo;

import br.com.tbc.agro.core.domain.dbs.dto.MovimentoMaoObraDTO;
import br.com.tbc.agro.core.domain.dbs.dto.ProcMovEquipamentosDTO;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "MovimentoEquipamentoMapping",
                classes = @ConstructorResult(
                        targetClass = ProcMovEquipamentosDTO.class,
                        columns = {
                                @ColumnResult(name = "cdEquipto", type = String.class),
                                @ColumnResult(name = "cdFrenTrab", type = Long.class),
                                @ColumnResult(name = "cdTipoDespesa", type = Long.class),
                                @ColumnResult(name = "qtTonelada", type = BigDecimal.class),
                                @ColumnResult(name = "qtHrKm", type = BigDecimal.class),
                                @ColumnResult(name = "vlDespesa", type = BigDecimal.class)
                        }
                )
        ),
        @SqlResultSetMapping(
                name = "MovimentoMaoObraMapping",
                classes = @ConstructorResult(
                        targetClass = MovimentoMaoObraDTO.class,
                        columns = {
                                @ColumnResult(name = "cdCcusto", type = Long.class),
                                @ColumnResult(name = "cdFrenTrab", type = Long.class),
                                @ColumnResult(name = "cdTipoDespesa", type = Long.class),
                                @ColumnResult(name = "vlDespesa", type = BigDecimal.class)
                        }
                )
        )
})
@Entity
@Table(name = "CFT_HISTORICO")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CftHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CFT_HIST_SEQ")
    @SequenceGenerator(
            name = "CFT_HIST_SEQ",
            sequenceName = "SEQ_CFT_HISTORICO",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "CD_FREN_TRAB")
    private Long cdFrenTrab;

    @Enumerated(EnumType.STRING)
    @Column(name = "FG_TP_RECURSO")
    private FgTpRecursoEnum fgTpRecurso;

    @Column(name = "CD_RECURSO")
    private Long cdRecurso;

    @Column(name = "CD_TIPO_DESPESA")
    private Long cdTipoDespesa;

    @Column(name = "DT_HISTORICO")
    private LocalDate dtHistorico;

    @Column(name = "VL_DESPESA")
    private BigDecimal vlDespesa;

    @Column(name = "QT_TONELADA")
    private BigDecimal qtTonelada;

    @Column(name = "QT_HR_KM")
    private BigDecimal qtHrKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "FG_TP_PROCESSO")
    private TipoProcessoEnum fgTpProcesso;
}
