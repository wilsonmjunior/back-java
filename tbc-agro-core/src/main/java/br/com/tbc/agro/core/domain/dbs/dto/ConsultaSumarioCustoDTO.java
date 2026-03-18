package br.com.tbc.agro.core.domain.dbs.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ConsultaSumarioCustoDTO {
    Long getCdFrenteTrab();
    String getDeFrenteTrab();
    LocalDateTime getPeriodoProcessamento();
    Long getCdRecurso();
    String getDeRecurso();
    String getTpRecurso();
    Long getCdTipoDespesa();
    String getDeTipoDespesa();
    BigDecimal getQtTonelada();
    BigDecimal getQtHrKm();
    BigDecimal getVlDespesa();
    BigDecimal getVlUnitHrKm();
    BigDecimal getVlUnitTon();
}
