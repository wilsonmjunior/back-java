package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.pimscs.vo.CftHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CftHistoricoRepository extends JpaRepository<CftHistorico, Long> {

    // 🔹 Delete por período + frentes
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
        DELETE FROM CftHistorico h
         WHERE h.dtHistorico BETWEEN :dtIni AND :dtFim
         AND ( :frentes IS NULL OR h.cdFrenTrab IN :frentes )
       """)
    //AND h.cdFrenTrab IN :frentes
    int excluirPeriodoPorFrentes(
            @Param("dtIni") LocalDate dtIni,
            @Param("dtFim") LocalDate dtFim,
            @Param("frentes") List<Long> frentes
    );


}