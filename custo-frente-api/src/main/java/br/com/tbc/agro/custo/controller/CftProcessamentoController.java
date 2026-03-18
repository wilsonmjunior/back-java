package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.pimscs.service.CftProcessamentoService;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.custo.cases.dto.CftProcessamentoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cft-processamento")
@Tag(name = "CFT Processamento",
        description = "[ /cft-processamento ] - Processamento de Custos da Frente")
public class CftProcessamentoController {

    private final CftProcessamentoService service;

    @PostMapping
    @Operation(summary = "Processar Custos",
            description = "Processar custo de Equipamentos ou Mão de Obra")
    public ResponseEntity<Void> processar(
            @RequestBody final CftProcessamentoRequestDTO body
    ) {

        log.info("c=CftProcessamentoController, m=processar, body={}", body);

        final CftProcessoId processoId = new CftProcessoId(
                body.tipoProcesso(),
                body.dtIniProcesso()
        );

        service.processar(
                processoId,
                body.dtIniProcesso(),
                body.dtFimProcesso(),
                body.frentesTrabalho()
        );

        return ResponseEntity.ok().build();
    }
}
