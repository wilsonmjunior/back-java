package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.pimscs.repository.ContaContabilRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.ContaContabil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaContabilService {

    private final ContaContabilRepository contaContabilRepository;

    public List<ContaContabil> selecionarTodos() {
        log.info("c=TipoDespesaService, m=selecionarTodos");

        return contaContabilRepository.findAll();
    }

}
