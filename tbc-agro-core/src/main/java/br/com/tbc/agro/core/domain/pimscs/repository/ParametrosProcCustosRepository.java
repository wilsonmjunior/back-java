package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.pimscs.vo.Parametros;
import br.com.tbc.agro.core.domain.pimscs.vo.ParametrosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametrosProcCustosRepository
        extends JpaRepository<Parametros, ParametrosId> {

    @Query("""
        SELECT p.valor
        FROM Parametros p
        WHERE p.id.secao = 'CSTG_C'
          AND p.id.entrada = 'DT_PROCESSO'
          AND p.id.instancia = 'CERRA'
    """)

    String obterUltimaDataProcessada(
            );
}
