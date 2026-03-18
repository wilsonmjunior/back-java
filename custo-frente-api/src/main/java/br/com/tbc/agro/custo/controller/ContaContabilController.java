package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.custo.cases.ContaContabilUseCaseController;
import br.com.tbc.agro.custo.cases.dto.ContaContabilDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/conta-contabil")
@Tag(name = "Conta contábil", description = "[ /conta-contabil ] - API de Conta contábil.")
public class ContaContabilController {

    private final ContaContabilUseCaseController useCase;

    @GetMapping
    @Operation(summary = "Buscar Todos", description = "Buscar todos os tipos de conta contábil")
    public ResponseEntity<List<ContaContabilDTO>> buscarTodos() {
        log.info("c=TipoDespesaController, m=buscarTodos");

        return useCase.selecionarTodos();
    }

}
