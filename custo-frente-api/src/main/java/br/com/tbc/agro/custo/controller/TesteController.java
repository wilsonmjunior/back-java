package br.com.tbc.agro.custo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ping")
@Tag(name = "Teste", description = "[ /ping ] - API de teste da aplicação no ar.")
public class TesteController {

    @GetMapping(value = "/")
    @Operation(summary = "Testar aplicação", description = "Retorna PONG se aplicação estiver OK")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
