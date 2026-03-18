package br.com.tbc.agro.core.domain.pimscs.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CftProcessoId implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "FG_TP_PROCESSO")
    private TipoProcessoEnum fgTpProcesso;

    @Column(name = "DT_INI_PROCESSO")
    private LocalDate dtIniProcesso;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CftProcessoId that = (CftProcessoId) o;
        return fgTpProcesso == that.fgTpProcesso &&
                Objects.equals(dtIniProcesso, that.dtIniProcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fgTpProcesso, dtIniProcesso);
    }
}
