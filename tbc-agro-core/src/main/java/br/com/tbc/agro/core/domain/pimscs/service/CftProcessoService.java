package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.dto.MessageDTO;
import br.com.tbc.agro.core.domain.exceptions.TBCNoContentException;
import br.com.tbc.agro.core.domain.exceptions.TBCValidationException;
import br.com.tbc.agro.core.domain.pimscs.repository.CftProcessoRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static br.com.tbc.agro.core.domain.pimscs.vo.FgProcessadoEnum.N;
import static br.com.tbc.agro.core.domain.pimscs.vo.FgProcessadoEnum.S;

@Slf4j
@Service
@RequiredArgsConstructor
public class CftProcessoService {

    private final CftProcessoRepository repository;

    public List<CftProcesso> selecionarTodos() {
        log.info("c=CftProcessoService, m=selecionarTodos");

        return repository.findAll();
    }

    public CftProcesso buscarPorId(final CftProcessoId id) {
        log.info("c=CftProcessoService, m=buscarPorId, id={}, msg=INIT", id);

        final var processo = repository.findById(id);

        if (processo.isEmpty()) {
            log.error("c=CftProcessoService, m=buscarPorId, id={}, msg=Processo não encontrado", id);
            throw new TBCNoContentException(new MessageDTO("Processo não encontrado"));
        }

        log.info("c=CftProcessoService, m=buscarPorId, id={}, msg=END", id);
        return processo.get();
    }

    @Transactional
    public CftProcesso salvar(final CftProcesso entity) {
        log.info("c=CftProcessoService, m=salvar, entity={}, msg=INIT", entity);

        validarObrigatorio(entity);

        if (repository.existsById(entity.getId())) {
            throw new TBCValidationException(
                    new MessageDTO("Processo já cadastrado")
            );
        }

        entity.setFgProcessado(N);

        final var handlerSave = repository.save(entity);

        log.info("c=CftProcessoService, m=salvar, entity={}, msg=END", handlerSave);
        return handlerSave;
    }



    @Transactional
    public CftProcesso alterarDataFim(
            final CftProcessoId id,
            final LocalDate novaDataFim
    ) {
        log.info("c=CftProcessoService, m=alterarDataFim, id={}, novaDtFimProcesso={}, msg=INIT",
                id, novaDataFim);

        if (novaDataFim == null) {
            throw new TBCValidationException(
                    new MessageDTO("Data final obrigatória")
            );
        }

        final var processo = buscarPorId(id);

        if (S.equals(processo.getFgProcessado())) {
            throw new TBCValidationException(
                    new MessageDTO("Não é permitido alterar período já processado")
            );
        }

        validarObrigatorio(processo);

        processo.setDtFimProcesso(novaDataFim);
        repository.save(processo);

        log.info("c=CftProcessoService, m=alterarDataFim, id={}, msg=END", id);
        return processo;
    }

    @Transactional
    public CftProcesso apagar(final CftProcessoId id) {
        log.info("c=CftProcessoService, m=apagar, id={}, msg=INIT", id);

        final var processo = buscarPorId(id);

        if (S.equals(processo.getFgProcessado())) {
            throw new TBCValidationException(
                    new MessageDTO("Não é permitido deletar período já processado")
            );
        }

        repository.delete(processo);
        log.info("c=CftProcessoService, m=apagar, id={}, msg=END", id);

        return processo;
    }

    private void validarObrigatorio(final CftProcesso handler) {
        if (Objects.isNull(handler.getId().getFgTpProcesso())) {
            throw new TBCValidationException(
                    new MessageDTO("Tipo do processo obrigatório"));
        }

        if (Objects.isNull(handler.getId().getDtIniProcesso()) || Objects.isNull(handler.getDtFimProcesso())) {
            throw new TBCValidationException(new MessageDTO("Datas não podem ser nulas"));
        }

        if (!handler.getId().getDtIniProcesso().isBefore(handler.getDtFimProcesso())) {
            throw new TBCValidationException(new MessageDTO("Data inicial deve ser menor que a data final"));
        }
    }

    public Optional<CftProcesso> listarNaoProcessados() {
        return repository.findByFgProcessadoOrderByIdDtIniProcessoAsc(N);
    }
}
