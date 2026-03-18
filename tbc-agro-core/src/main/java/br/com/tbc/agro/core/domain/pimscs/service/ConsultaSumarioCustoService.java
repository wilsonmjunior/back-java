package br.com.tbc.agro.core.domain.pimscs.service;


import br.com.tbc.agro.core.domain.dbs.dto.ConsultaSumarioCustoDTO;
import br.com.tbc.agro.core.domain.pimscs.repository.ConsultaSumarioCustoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaSumarioCustoService {
    private final ConsultaSumarioCustoRepository consultaSumarioCustoRepository;

    public List<ConsultaSumarioCustoDTO> buscar(
            final LocalDateTime dtIni,
            final LocalDateTime dtFim,
            final Long frente,
            final Long equip,
            final Long tipoDesp
    ) {
        return consultaSumarioCustoRepository.buscarResumo(dtIni, dtFim, frente, equip, tipoDesp);
    }
}
