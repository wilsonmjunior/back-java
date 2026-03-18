package br.com.tbc.agro.core.domain.pimscs.service;


import br.com.tbc.agro.core.domain.dbs.dto.ProcMovEquipamentosDTO;
import br.com.tbc.agro.core.domain.dto.MessageDTO;
import br.com.tbc.agro.core.domain.exceptions.TBCValidationException;
import br.com.tbc.agro.core.domain.pimscs.repository.CftHistoricoRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.CftProcessoRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.MovimentoMaoObraRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.ParametrosGerHistManejoRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.ParametrosProcCustosRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.ProcMovEquipamentosRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.CftHistorico;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.FgTpRecursoEnum;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


import static br.com.tbc.agro.core.domain.pimscs.vo.FgProcessadoEnum.N;

@Slf4j
@Service
@RequiredArgsConstructor
public class CftProcMovEquipamentosService {

    private final CftHistoricoRepository cftHistoricoRepository;
    private final CftProcessoRepository cftProcessoRepository;
    private final ParametrosProcCustosRepository parametrosProcCustosRepository;
    private final ParametrosGerHistManejoRepository parametrosGerHistManejoRepository;
    private final ProcMovEquipamentosRepository movimentoEquipamentoRepository;
    private final MovimentoMaoObraRepository movimentoMaoObraRepository;


    private final DateTimeFormatter formatoParametro = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional
    public void processar(
            final CftProcessoId processoId,
            final LocalDate dtIni,
            final LocalDate dtFim,
            final List<Long> frentes
    ) {


        final List<Long> filtroFrentes = (frentes == null || frentes.isEmpty())
                ? null
                : frentes;

        cftHistoricoRepository.excluirPeriodoPorFrentes(dtIni, dtFim, filtroFrentes);
        processarEquipamentos(dtIni, dtFim);

        log.info("c=CftProcMovEquipamentosService, m=processar, msg=END");
    }


    private void validarPeriodo(final LocalDate dtIni, final LocalDate dtFim) {
        if (dtIni == null || dtFim == null || dtIni.isAfter(dtFim)) {
            throw new TBCValidationException(
                    new MessageDTO("Período de processamento inválido")
            );
        }
    }

    private void validarOrdemProcessamento(final CftProcessoId idSolicitado) {
        final var proximo = cftProcessoRepository
                .findByFgProcessadoOrderByIdDtIniProcessoAsc(N)
                .orElseThrow(() -> new TBCValidationException(
                        new MessageDTO("Não existe período pendente para processamento")
                ));

        if (!proximo.getId().equals(idSolicitado)) {
            throw new TBCValidationException(
                    new MessageDTO(
                            "O período deve ser processado em ordem cronológica. Próximo período válido: "
                                    + proximo.getId().getDtIniProcesso()
                    )
            );
        }
    }


    private void validarProcessamentoCustos(final LocalDate dtFim) {

        final String valor = parametrosProcCustosRepository.obterUltimaDataProcessada();

        if (valor == null || valor.isBlank()) {
            throw new TBCValidationException(
                    new MessageDTO("Parâmetro CSTG_C / DT_PROCESSO não configurado")
            );
        }

        final LocalDate dtProcesso;
        try {
            dtProcesso = LocalDate.parse(valor, formatoParametro);
        } catch (DateTimeParseException e) {
            throw new TBCValidationException(
                    new MessageDTO("Parâmetro CSTG_C / DT_PROCESSO inválido: " + valor)
            );
        }

        if (dtFim.isAfter(dtProcesso)) {
            throw new TBCValidationException(
                    new MessageDTO(
                            "O processamento de custos não foi realizado até "
                                    + dtFim + ". Última data processada: " + dtProcesso
                    )
            );
        }
    }

    private void validarGeracaoHistManejo(final LocalDate dtFim) {

        final String valor = parametrosGerHistManejoRepository.obterValorParametro(
                "ATRC_MEC",
                "DT_PROCESSO"
        );

        if (valor == null || valor.isBlank()) {
            throw new TBCValidationException(
                    new MessageDTO("Parâmetro ATRC_MEC / DT_PROCESSO não configurado")
            );
        }

        final LocalDate dtProcessoHistManejo;
        try {
            dtProcessoHistManejo = LocalDate.parse(valor, formatoParametro);
        } catch (DateTimeParseException e) {
            throw new TBCValidationException(
                    new MessageDTO("Parâmetro ATRC_MEC / DT_PROCESSO inválido: " + valor)
            );
        }

        if (dtFim.isAfter(dtProcessoHistManejo)) {
            throw new TBCValidationException(
                    new MessageDTO(
                            "A geração do Histmanejo não foi realizada até "
                                    + dtFim
                                    + ". Última data processada: " + dtProcessoHistManejo
                    )
            );

        }
    }


    @Transactional
    private void processarEquipamentos(
            final LocalDate dtIni,
            final LocalDate dtFim
    ) {

        final List<ProcMovEquipamentosDTO> movimentos =
                movimentoEquipamentoRepository.buscarMovimentos(dtIni, dtFim);

        if (movimentos.isEmpty()) {
            log.info("Nenhum movimento de equipamentos encontrado.");
            return;
        }

        final List<CftHistorico> historicos = movimentos.stream().map(dto -> {
            final CftHistorico hist = new CftHistorico();

            hist.setCdFrenTrab(dto.cdFrenTrab());
            hist.setFgTpRecurso(FgTpRecursoEnum.E);
            hist.setCdRecurso(Long.valueOf(dto.cdEquipto()));
            hist.setCdTipoDespesa(dto.cdTipoDespesa());
            hist.setDtHistorico(dtIni);
            hist.setVlDespesa(dto.vlDespesa());
            hist.setQtTonelada(dto.qtTonelada());
            hist.setQtHrKm(dto.qtHrKm());
            hist.setFgTpProcesso(TipoProcessoEnum.C);      // FIXO P

            return hist;
        }).toList();

        cftHistoricoRepository.saveAll(historicos);

        log.info("Movimentos de equipamentos gerados: {}", historicos.size());
    }

}
