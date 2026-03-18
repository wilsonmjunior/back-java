package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.pimscs.service.CftProcessamentoService;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import br.com.tbc.agro.custo.cases.dto.CftProcessamentoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cft/processamento")
@RequiredArgsConstructor
@Slf4j
public class CftProcessamentoUseCaseController {

    private final CftProcessamentoService cftProcessamentoService;


    public ResponseEntity<Void> processar(
            @PathVariable final TipoProcessoEnum tipo,
            @org.springframework.web.bind.annotation.RequestBody
            final CftProcessamentoRequestDTO dto
    ) {

        log.info(
                "UseCase processamento tipo={} dtIni={}, dtFim={}, frentes={}",
                tipo,
                dto.dtIniProcesso(),
                dto.dtFimProcesso(),
                dto.frentesTrabalho()
        );

        final CftProcessoId processoId = new CftProcessoId(
                tipo,
                dto.dtIniProcesso()
        );

        // Não precisa switch, o service já trata o tipo
        cftProcessamentoService.processar(
                processoId,
                dto.dtIniProcesso(),
                dto.dtFimProcesso(),
                dto.frentesTrabalho()
        );

        return ResponseEntity.ok().build();
    }
}
