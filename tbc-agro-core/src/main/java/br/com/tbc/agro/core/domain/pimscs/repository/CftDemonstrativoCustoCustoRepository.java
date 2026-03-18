package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.dbs.dto.DemonstrativoCustoCustoDTO;
import br.com.tbc.agro.core.domain.pimscs.vo.CftHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CftDemonstrativoCustoCustoRepository extends JpaRepository<CftHistorico, Long> {

    @Query(value = """
        SELECT
            H.CD_FREN_TRAB  AS CD_FRENTE_TRAB,
            FT.DE_FREN_TRAB AS DE_FRENTE_TRAB,
            H.DT_HISTORICO AS PERIODO_PROCESSAMENTO,
            H.CD_RECURSO,
            CASE
                WHEN H.FG_TP_RECURSO = 'E' THEN
                     M.DE_MODELO ||
                     CASE
                        WHEN G.CD_UNIMED IS NOT NULL 
                        THEN ' (' || G.CD_UNIMED || ')'
                     END
                WHEN H.FG_TP_RECURSO = 'M' THEN
                     CC.DE_CCUSTO
            END AS DE_RECURSO,
            H.FG_TP_RECURSO AS TP_RECURSO,
            H.CD_TIPO_DESPESA,
            TD.DE_TIPO_DESPESA,
            H.QT_TONELADA,
            H.QT_HR_KM,
            H.VL_DESPESA,
            CASE WHEN NVL(H.QT_HR_KM,0) > 0 
                 THEN ROUND(H.VL_DESPESA / H.QT_HR_KM, 2) 
            END AS VL_UNIT_HR_KM,
            CASE WHEN NVL(H.QT_TONELADA,0) > 0 
                 THEN ROUND(H.VL_DESPESA / H.QT_TONELADA, 2) 
            END AS VL_UNIT_TON
        FROM CFT_HISTORICO H
        LEFT JOIN FREN_TRAB FT 
               ON FT.CD_FREN_TRAB = H.CD_FREN_TRAB
        LEFT JOIN CFT_TIPO_DESPESA_HE TD 
               ON TD.CD_TIPO_DESPESA = H.CD_TIPO_DESPESA
        LEFT JOIN EQUIPTOS EQ 
               ON EQ.CD_EQUIPTO = H.CD_RECURSO 
              AND H.FG_TP_RECURSO = 'E'
        LEFT JOIN MODELOS M 
               ON M.CD_MODELO = EQ.CD_MODELO
        LEFT JOIN GRUOPERATI G 
               ON G.CD_GRUPO_OP = M.CD_GRUPO_OP
        LEFT JOIN CC_CSTG CC 
               ON CC.CD_CCUSTO = H.CD_RECURSO 
              AND H.FG_TP_RECURSO = 'M'
        WHERE (:pDtIni IS NULL OR H.DT_HISTORICO >= :pDtIni)
          AND (:pDtFim IS NULL OR H.DT_HISTORICO <= :pDtFim)
          AND (:pFrente IS NULL OR H.CD_FREN_TRAB = :pFrente)
          AND (:pTipoDesp IS NULL OR H.CD_TIPO_DESPESA = :pTipoDesp)
        ORDER BY H.DT_HISTORICO, 
                 H.CD_FREN_TRAB, 
                 H.FG_TP_RECURSO, 
                 H.CD_RECURSO, 
                 H.CD_TIPO_DESPESA
        """, nativeQuery = true)
    List<DemonstrativoCustoCustoDTO> buscarDemonstrativo(
            @Param("pDtIni") LocalDate dtIni,
            @Param("pDtFim") LocalDate dtFim,
            @Param("pFrente") Long frente,
            @Param("pTipoDesp") String tipoDesp
    );
}