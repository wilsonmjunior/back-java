package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.dbs.dto.ProcMovEquipamentosDTO;
import br.com.tbc.agro.core.domain.pimscs.repository.CftHistoricoRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.MovimentoMaoObraRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.CftHistorico;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.FgTpRecursoEnum;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CftProcMovMaoObraService {

    private final MovimentoMaoObraRepository movimentoMaoObraRepository;
    private final CftHistoricoRepository cftHistoricoRepository;

    @Transactional
    public void processar(
            final CftProcessoId processoId,
            final LocalDate dtIni,
            final LocalDate dtFim,
            final List<Long> longs) {

        final List<ProcMovEquipamentosDTO> movimentos =
                movimentoMaoObraRepository.buscarMovimentos(dtIni, dtFim);

        if (movimentos.isEmpty()) {
            log.info("Nenhum movimento de mão de obra encontrado.");
            return;
        }

        final List<CftHistorico> historicos = movimentos.stream().map(dto -> {

            final CftHistorico hist = new CftHistorico();

            hist.setCdFrenTrab(dto.cdFrenTrab());
            hist.setFgTpRecurso(FgTpRecursoEnum.M);      // FIXO M
            hist.setCdRecurso(Long.valueOf(dto.cdEquipto()));         // Centro de Custo
            hist.setCdTipoDespesa(dto.cdTipoDespesa());
            hist.setDtHistorico(dtIni);
            hist.setVlDespesa(dto.vlDespesa());
            hist.setQtTonelada(BigDecimal.ZERO);        // FIXO 0
            hist.setQtHrKm(BigDecimal.ZERO);
            hist.setFgTpProcesso(TipoProcessoEnum.O);


            return hist;

        }).toList();

        cftHistoricoRepository.saveAll(historicos);

        log.info("Movimentos de mão de obra gerados: {}", historicos.size());
    }
}
