package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.dbs.dto.DemonstrativoCustoCustoDTO;
import br.com.tbc.agro.core.domain.pimscs.repository.CftDemonstrativoCustoCustoRepository;
import br.com.tbc.agro.core.domain.pimscs.service.CustoFrenteTrabalhoService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cft/resumo-frente-trabalho")
@RequiredArgsConstructor
public class ConsultaCustoFrenteTrabalhoController {

    private final CftDemonstrativoCustoCustoRepository cftConsultaSumarioCustoRepository;


    private final CustoFrenteTrabalhoService custoFrenteTrabalhoService;

    @GetMapping()
    public List<DemonstrativoCustoCustoDTO> buscar(
            @RequestParam(required = false) final LocalDate dtIni,
            @RequestParam(required = false) final LocalDate dtFim,
            @RequestParam(required = false) final Long frente,
            @RequestParam(required = false) final String tipoDesp
    ) {
        return cftConsultaSumarioCustoRepository.buscarDemonstrativo(dtIni, dtFim, frente, tipoDesp);
    }}