package br.com.tbc.agro.core.domain.dbs.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface DemonstrativoCustoCustoDTO {
    Long getCdFrenteTrab();
    String getDeFrenteTrab();
    LocalDateTime getPeriodoProcessamento();
    BigDecimal getCdRecurso();
    String getDeRecurso();
    String getTpRecurso();
    String getCdTipoDespesa();
    String getDeTipoDespesa();
    BigDecimal getQtTonelada();
    BigDecimal getQtHrKm();
    BigDecimal getVlDespesa();
    BigDecimal getVlUnitHrKm();
    BigDecimal getVlUnitTon();
}