package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.dbs.dto.ProcMovEquipamentosDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProcMovEquipamentosRepository {

    @PersistenceContext
    private EntityManager em;


    public List<ProcMovEquipamentosDTO> buscarMovimentos(
            final LocalDate dtIni,
            final LocalDate dtFim
    ) {

        final String sql = """
            SELECT 
                E.CD_EQUIPTO            AS cdEquipto,
                F.CD_FREN_TRAB          AS cdFrenTrab,
                T.CD_TIPO_DESPESA       AS cdTipoDespesa,
                NVL(H.QT_TONELADA, 0)   AS qtTonelada,
                NVL(M.QT_HR_KM, 0)      AS qtHrKm,
                SUM(P.VL_CONSUM)        AS vlDespesa
            FROM EQUIPTOS E
            JOIN FREN_TRAB F
              ON F.CD_FREN_TRAN = E.CD_FREN_TRAN
            JOIN PLCOPER P
              ON P.CD_EQUIPTO = E.CD_EQUIPTO
            JOIN CFT_TIPO_DESPESA_DE T
              ON T.CD_CONTA = P.CD_CONTA
            LEFT JOIN (
                SELECT 
                    CD_EQUIPTO,
                    SUM(QT_CANA_ENT) / 1000 AS QT_TONELADA
                FROM HISTPRDEQU
                WHERE DT_HISTORICO BETWEEN :dtIni AND :dtFim
                GROUP BY CD_EQUIPTO
            ) H ON H.CD_EQUIPTO = E.CD_EQUIPTO
            LEFT JOIN (
                SELECT 
                    CD_RECURSO AS CD_EQUIPTO,
                    SUM(QT_PROD_REC) AS QT_HR_KM
                FROM HISTMANEJO
                WHERE DT_HISTORICO BETWEEN :dtIni AND :dtFim
                  AND CD_TP_REC = 'E'
                  AND CD_HIST = 'R'
                GROUP BY CD_RECURSO
            ) M ON M.CD_EQUIPTO = E.CD_EQUIPTO
            WHERE P.CD_OPERAC = 0
              AND P.FG_PLA_CST = 'C'
              AND P.CD_COMPO IS NOT NULL
              AND P.DT_HISTORI BETWEEN :dtIni AND :dtFim
            GROUP BY 
                E.CD_EQUIPTO,
                F.CD_FREN_TRAB,
                T.CD_TIPO_DESPESA,
                H.QT_TONELADA,
                M.QT_HR_KM
        """;

        final Query query = em.createNativeQuery(sql, "MovimentoEquipamentoMapping");
        query.setParameter("dtIni", dtIni);
        query.setParameter("dtFim", dtFim);

        return query.getResultList();
    }
}
