package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.dbs.dto.ConsultaSumarioCustoDTO;
import br.com.tbc.agro.core.domain.pimscs.service.ConsultaSumarioCustoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sumario")
@RequiredArgsConstructor
public class ConsultaSumarioCustoController {

    private final ConsultaSumarioCustoService service;

    @GetMapping()
    public List<ConsultaSumarioCustoDTO> buscar(
            @Parameter(schema = @Schema(type = "string", format = "date"))
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate dtIni,
            @Parameter(schema = @Schema(type = "string", format = "date"))
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate dtFim,
            @RequestParam(required = false) final Long frente,
            @RequestParam(required = false) final Long equip,
            @RequestParam(required = false) final Long tipoDesp
    ) {
        return service.buscar(dtIni.atStartOfDay(), dtFim.atStartOfDay(), frente, equip, tipoDesp);
    }
}