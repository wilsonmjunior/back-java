package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.pimscs.service.CftProcessamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cft/reversao")
@RequiredArgsConstructor
@Tag(name = "CFT Reversão Processamento", description = "[ /cft-reversão ] - Reversão de Custos da Frente")
public class ReversaoCustoFrenteController {

    private final CftProcessamentoService service;

    @Operation(summary = "Reverter último período processado do Custo da Frente")
    @PostMapping("/reverter")
    public ResponseEntity<String> reverterUltimoPeriodo() {

        service.reverterUltimoPeriodo();

        return ResponseEntity.ok("Reversão realizada com sucesso.");
    }
}

