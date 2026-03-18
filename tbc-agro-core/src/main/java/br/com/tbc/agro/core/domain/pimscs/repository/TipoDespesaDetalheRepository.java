package br.com.tbc.agro.core.domain.pimscs.repository;

import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalhe;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalheId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TipoDespesaDetalheRepository extends JpaRepository<TipoDespesaDetalhe, TipoDespesaDetalheId> {

    @Modifying
    @Query(value = "DELETE FROM CFT_TIPO_DESPESA_DE WHERE CD_TIPO_DESPESA = :tipoDespesa AND CD_CONTA = :conta", nativeQuery = true)
    void deleteContaByTipoDespesa(final String conta, final Long tipoDespesa);
}