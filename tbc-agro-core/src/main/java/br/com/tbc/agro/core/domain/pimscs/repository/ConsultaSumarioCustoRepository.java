package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.dbs.dto.ConsultaSumarioCustoDTO;
import br.com.tbc.agro.core.domain.pimscs.vo.CftHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaSumarioCustoRepository extends JpaRepository<CftHistorico, Long> {

    @Query(value = """
        SELECT
            H.CD_FREN_TRAB              AS cdFrenteTrab,
            FT.DE_FREN_TRAB             AS deFrenteTrab,
            H.DT_HISTORICO              AS periodoProcessamento,
            H.CD_RECURSO                AS cdRecurso,
            CASE
                WHEN H.FG_TP_RECURSO = 'E'
                    THEN M.DE_MODELO ||
                         CASE WHEN G.CD_UNIMED IS NOT NULL
                              THEN ' (' || G.CD_UNIMED || ')'
                         END
                WHEN H.FG_TP_RECURSO = 'M'
                    THEN CC.DE_CCUSTO
            END                         AS deRecurso,
            H.FG_TP_RECURSO              AS tpRecurso,
            H.CD_TIPO_DESPESA            AS cdTipoDespesa,
            TD.DE_TIPO_DESPESA           AS deTipoDespesa,
            SUM(NVL(H.QT_TONELADA,0))    AS qtTonelada,
            SUM(NVL(H.QT_HR_KM,0))       AS qtHrKm,
            SUM(NVL(H.VL_DESPESA,0))     AS vlDespesa,
            CASE
                WHEN SUM(NVL(H.QT_HR_KM,0)) > 0
                THEN ROUND(SUM(NVL(H.VL_DESPESA,0)) /
                           SUM(NVL(H.QT_HR_KM,0)), 2)
            END                         AS vlUnitHrKm,
            CASE
                WHEN SUM(NVL(H.QT_TONELADA,0)) > 0
                THEN ROUND(SUM(NVL(H.VL_DESPESA,0)) /
                           SUM(NVL(H.QT_TONELADA,0)), 2)
            END                         AS vlUnitTon
        FROM PIMS.CFT_HISTORICO H
        LEFT JOIN PIMS.FREN_TRAB FT ON FT.CD_FREN_TRAB = H.CD_FREN_TRAB
        LEFT JOIN PIMS.CFT_TIPO_DESPESA_HE TD ON TD.CD_TIPO_DESPESA = H.CD_TIPO_DESPESA
        LEFT JOIN PIMS.EQUIPTOS EQ ON EQ.CD_EQUIPTO = H.CD_RECURSO AND H.FG_TP_RECURSO = 'E'
        LEFT JOIN PIMS.MODELOS M ON M.CD_MODELO = EQ.CD_MODELO
        LEFT JOIN PIMS.GRUOPERATI G ON G.CD_GRUPO_OP = M.CD_GRUPO_OP
        LEFT JOIN PIMS.CC_CSTG CC ON CC.CD_CCUSTO = H.CD_RECURSO AND H.FG_TP_RECURSO = 'M'
        WHERE (:dtIni IS NULL OR H.DT_HISTORICO >= :dtIni)
          AND (:dtFim IS NULL OR H.DT_HISTORICO <= :dtFim)
          AND (:frente IS NULL OR H.CD_FREN_TRAB = :frente)
          AND (:equip IS NULL OR (H.FG_TP_RECURSO = 'E' AND H.CD_RECURSO = :equip))
          AND (:tipoDesp IS NULL OR H.CD_TIPO_DESPESA = :tipoDesp)
        GROUP BY
            H.CD_FREN_TRAB,
            FT.DE_FREN_TRAB,
            H.DT_HISTORICO,
            H.CD_RECURSO,
            H.FG_TP_RECURSO,
            M.DE_MODELO,
            G.CD_UNIMED,
            CC.DE_CCUSTO,
            H.CD_TIPO_DESPESA,
            TD.DE_TIPO_DESPESA
        ORDER BY
            H.CD_FREN_TRAB,
            H.DT_HISTORICO,
            H.FG_TP_RECURSO,
            H.CD_RECURSO
        """,
            nativeQuery = true)
    List<ConsultaSumarioCustoDTO> buscarResumo(
            @Param("dtIni") LocalDateTime dtIni,
            @Param("dtFim") LocalDateTime dtFim,
            @Param("frente") Long frente,
            @Param("equip") Long equip,
            @Param("tipoDesp") Long tipoDesp
    );
}