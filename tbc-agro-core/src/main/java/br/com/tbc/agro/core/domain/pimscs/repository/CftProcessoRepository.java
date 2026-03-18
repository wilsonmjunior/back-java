package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.FgProcessadoEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CftProcessoRepository extends JpaRepository<CftProcesso, CftProcessoId> {

    Optional<CftProcesso>
    findByFgProcessadoOrderByIdDtIniProcessoAsc(FgProcessadoEnum fgProcessado);

    @Query(value = """
        SELECT *
        FROM (
            SELECT *
            FROM CFT_PROCESSO
            WHERE FG_PROCESSADO = :fg
            ORDER BY DT_INI_PROCESSO DESC
        )
        WHERE ROWNUM = 1
        """, nativeQuery = true)
    CftProcesso buscarUltimoProcessado(@Param("fg") String fg);

    boolean existsById(CftProcessoId id);
}
