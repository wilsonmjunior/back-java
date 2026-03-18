package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.pimscs.service.FrenteTrabalhoService;
import br.com.tbc.agro.core.domain.pimscs.vo.FrenteTrabalho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrenteTrabalhoUseCase {

    private final FrenteTrabalhoService service;

    public List<FrenteTrabalho> findAll() {
        return service.findAll();
    }
}
