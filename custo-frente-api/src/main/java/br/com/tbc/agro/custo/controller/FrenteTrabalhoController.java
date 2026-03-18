package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.pimscs.vo.FrenteTrabalho;
import br.com.tbc.agro.custo.cases.FrenteTrabalhoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/frente")
@Tag(name = "frent", description = "[ /frente ] - API de busca de frentes de trabalho.")
public class FrenteTrabalhoController {

    private final FrenteTrabalhoUseCase useCase;

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Buscar todas as frentes de trabalho")
    public ResponseEntity<List<FrenteTrabalho>> buscarTodos() {
        log.info("c=FrenteTrabalhoController, m=buscarTodos");
        return ResponseEntity.ok(useCase.findAll());
    }

}
