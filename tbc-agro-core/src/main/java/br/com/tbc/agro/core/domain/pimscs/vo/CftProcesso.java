package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CFT_PROCESSO")
public class CftProcesso {

    @EmbeddedId
    private CftProcessoId id;

    @Column(name = "DT_FIM_PROCESSO")
    private LocalDate dtFimProcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "FG_PROCESSADO")
    private FgProcessadoEnum fgProcessado;
}
