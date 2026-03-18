package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.pimscs.service.ContaContabilService;
import br.com.tbc.agro.core.domain.pimscs.vo.ContaContabil;
import br.com.tbc.agro.custo.cases.dto.ContaContabilDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaContabilUseCaseController {

    private final ContaContabilService service;

    public ResponseEntity<List<ContaContabilDTO>> selecionarTodos() {
        final var data = service.selecionarTodos();
        final var response = new ArrayList<ContaContabilDTO>();

        for (ContaContabil contaContabil : data) {
            response.add(ContaContabilDTO.from(contaContabil));
        }

        return ResponseEntity.ok(response);
    }
}
