package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.custo.cases.TipoDespesaUseCaseController;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaDTO;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaRequestSaveDTO;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaRequestUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tipo-despesa")
@Tag(name = "Tipo despesa", description = "[ /tipo-despesa ] - API de Tipos de despesa.")
public class TipoDespesaController {

    private final TipoDespesaUseCaseController useCase;

    @GetMapping
    @Operation(summary = "Buscar Todos", description = "Buscar todos os tipos de despesas")
    public ResponseEntity<List<TipoDespesaDTO>> buscarTodos() {
        log.info("c=TipoDespesaController, m=buscarTodos");

        return useCase.selecionarTodos();
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar por código", description = "Buscar tipo de desposa por código")
    public ResponseEntity<TipoDespesaDTO> buscarPorCodigo(@PathVariable final Long codigo) {
        log.info("c=TipoDespesaController, m=buscarPorCodigo, codigo={}", codigo);

        return useCase.buscarPorCodigo(codigo);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Apagar", description = "Apagar tipo de despesa")
    public ResponseEntity<TipoDespesaDTO> apagar(@PathVariable final Long codigo) {
        log.info("c=TipoDespesaController, m=apagar, codigo={}", codigo);

        return useCase.apagar(codigo);
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Alterar", description = "Alterar tipo de despesa")
    public ResponseEntity<TipoDespesaDTO> alterar(@PathVariable final Long codigo,
                                                  @RequestBody final TipoDespesaRequestUpdateDTO body) {
        log.info("c=TipoDespesaController, m=alterar, codigo={}, body={}", codigo, body);

        return useCase.alterar(codigo, body);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar tipo de despesa")
    public ResponseEntity<TipoDespesaDTO> salvar(@RequestBody final TipoDespesaRequestSaveDTO body) {
        log.info("c=TipoDespesaController, m=salvar, body={}", body);

        return useCase.salvar(body);
    }

    @PostMapping("/adicionar-conta/{codigo}/conta/{conta}")
    @Operation(summary = "Adicionar conta", description = "Adicionar conta do tipo de despesa")
    public ResponseEntity<TipoDespesaDTO> adicionarConta(@PathVariable final Long codigo,
                                                         @PathVariable final String conta) {
        log.info("c=TipoDespesaController, m=adicionarConta, codigo={}, conta={}", codigo, conta);

        return useCase.adicionarConta(codigo, conta);
    }

    @DeleteMapping("/remover-conta/{codigo}/conta/{conta}")
    @Operation(summary = "Remover conta", description = "Remover conta do tipo de despesa")
    public ResponseEntity<TipoDespesaDTO> removerConta(@PathVariable final Long codigo,
                                                       @PathVariable final String conta) {
        log.info("c=TipoDespesaController, m=removerConta, codigo={}, conta={}", codigo, conta);

        return useCase.removerConta(codigo, conta);
    }
}
