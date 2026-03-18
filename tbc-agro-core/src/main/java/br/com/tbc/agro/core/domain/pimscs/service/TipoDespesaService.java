package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.dto.MessageDTO;
import br.com.tbc.agro.core.domain.exceptions.TBCNoContentException;
import br.com.tbc.agro.core.domain.exceptions.TBCValidationException;
import br.com.tbc.agro.core.domain.pimscs.repository.ContaContabilRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.TipoDespesaDetalheRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.TipoDespesaRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.ContaContabil;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesa;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalhe;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesaDetalheId;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoDespesaService {

    private final TipoDespesaRepository tipoDespesaRepository;
    private final TipoDespesaDetalheRepository tipoDespesaDetalheRepository;
    private final ContaContabilRepository contaContabilRepository;

    public List<TipoDespesa> selecionarTodos() {
        log.info("c=TipoDespesaService, m=selecionarTodos");

        return tipoDespesaRepository.findAll();
    }

    @Transactional
    public TipoDespesa salvar(final TipoDespesa tipoDespesa, final List<String> contas) {

        if (tipoDespesaRepository.existsById(tipoDespesa.getCodigo())) {
            throw new TBCValidationException(
                    new MessageDTO("Tipo de despesa já cadastrada")
            );
        }

        if (contas == null || contas.isEmpty()) {
            throw new TBCValidationException(
                    new MessageDTO("Conta contábil obrigatório")
            );
        }

        final var contasEncontradas = new ArrayList<ContaContabil>();

        for (String conta : contas) {
            final var contaContabil = buscarConta(conta);

            contasEncontradas.add(contaContabil);
        }

        final var handlerSave = tipoDespesaRepository.save(tipoDespesa);


        for (ContaContabil handlerContaContabil : contasEncontradas) {
            final var detalhe = new TipoDespesaDetalhe();

            detalhe.setId(new TipoDespesaDetalheId(handlerSave.getCodigo(), handlerContaContabil.getCodigo()));
            detalhe.setTipoDespesa(handlerSave);
            detalhe.setContaContabil(handlerContaContabil);

            tipoDespesaDetalheRepository.save(detalhe);
        }

        return handlerSave;
    }

    @Transactional
    public TipoDespesa alterar(final TipoDespesa tipoDespesa) {
        log.info("c=TipoDespesaService, m=alterar, tipoDespesa={}, msg=INIT", tipoDespesa);

        final var tipoDespesaBusca = buscarPorCodigo(tipoDespesa.getCodigo());

        tipoDespesaBusca.setDescricao(tipoDespesa.getDescricao());
        tipoDespesaRepository.save(tipoDespesaBusca);

        log.info("c=TipoDespesaService, m=alterar, tipoDespesa={}, msg=END", tipoDespesa);

        return tipoDespesaBusca;
    }

    @Transactional
    public TipoDespesa apagar(final Long codigo) {
        log.info("c=TipoDespesaService, m=apagar, codigo={}, msg=INIT", codigo);

        final var tipoDespesa = buscarPorCodigo(codigo);

        tipoDespesaRepository.delete(tipoDespesa);

        log.info("c=TipoDespesaService, m=apagar, codigo={}, msg=END", codigo);

        return tipoDespesa;
    }

    @Transactional
    public TipoDespesa adicionarConta(final Long codigoTipoDespesa, final String codigoConta) {
        log.info("c=TipoDespesaService, m=adicionarConta, codigoTipoDespesa={}, codigoConta={}, msg=INIT",
                codigoTipoDespesa, codigoConta);

        final var tipoDespesa = buscarPorCodigo(codigoTipoDespesa);
        final var contaContabil = buscarConta(codigoConta);

        final var detalhe = new TipoDespesaDetalhe();
        detalhe.setId(new TipoDespesaDetalheId(tipoDespesa.getCodigo(), contaContabil.getCodigo()));
        detalhe.setTipoDespesa(tipoDespesa);
        detalhe.setContaContabil(contaContabil);

        tipoDespesaDetalheRepository.save(detalhe);

        tipoDespesaDetalheRepository.save(detalhe);

        log.info("c=TipoDespesaService, m=adicionarConta, codigoTipoDespesa={}, codigoConta={}, msg=END",
                codigoTipoDespesa, codigoConta);

        return tipoDespesa;
    }

    @Transactional
    public TipoDespesa removerConta(final Long codigoTipoDespesa, final String codigoConta) {
        log.info("c=TipoDespesaService, m=removerConta, codigoTipoDespesa={}, codigoConta={}, msg=INIT",
                codigoTipoDespesa, codigoConta);

        final var tipoDespesa = buscarPorCodigo(codigoTipoDespesa);
        final var contaContabil = buscarConta(codigoConta);

        tipoDespesaDetalheRepository.deleteContaByTipoDespesa(contaContabil.getCodigo(), tipoDespesa.getCodigo());

        log.info("c=TipoDespesaService, m=removerConta, codigoTipoDespesa={}, codigoConta={}, msg=END",
                codigoTipoDespesa, codigoConta);

        return tipoDespesa;
    }

    public TipoDespesa buscarPorCodigo(final Long codigo) {
        log.info("c=TipoDespesaService, m=buscarPorCodigo, codigo={}, msg=INIT", codigo);

        final var tipoDespesa = tipoDespesaRepository.findById(codigo);

        if (tipoDespesa.isEmpty()) {
            log.error("c=TipoDespesaService, m=apagar, codigo={}, msg=Tipo de despesa não encontrado", codigo);

            throw new TBCNoContentException(new MessageDTO("Código não encontrado"));
        }

        log.info("c=TipoDespesaService, m=buscarPorCodigo, codigo={}, msg=END", codigo);

        return tipoDespesa.get();
    }

    public ContaContabil buscarConta(final String codigo) {
        log.info("c=TipoDespesaService, m=buscarConta, codigo={}, msg=INIT", codigo);

        final var contaContabil = contaContabilRepository.findById(codigo);

        if (contaContabil.isEmpty()) {
            log.error("c=TipoDespesaService, m=buscarConta, codigo={}, msg=Conta contábil não encontrada", codigo);

            throw new TBCValidationException(new MessageDTO("Conta contábil não encontrada"));
        }

        log.info("c=TipoDespesaService, m=buscarConta, codigo={}, msg=END", codigo);

        return contaContabil.get();
    }
}
