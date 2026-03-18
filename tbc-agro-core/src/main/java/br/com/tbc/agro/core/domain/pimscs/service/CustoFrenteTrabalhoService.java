package br.com.tbc.agro.core.domain.pimscs.service;

import br.com.tbc.agro.core.domain.dbs.dto.DemonstrativoCustoCustoDTO;

import br.com.tbc.agro.core.domain.pimscs.repository.CftDemonstrativoCustoCustoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustoFrenteTrabalhoService {

    private final CftDemonstrativoCustoCustoRepository repository;

    public List<DemonstrativoCustoCustoDTO> buscarDemonstrativo(
            final LocalDate dtIni,
            final LocalDate dtFim,
            final Long frente,
            final String tipoDesp
    ) {
        return repository.buscarDemonstrativo(dtIni, dtFim, frente, tipoDesp);
    }
}