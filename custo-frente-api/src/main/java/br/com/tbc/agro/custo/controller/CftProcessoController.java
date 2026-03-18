package br.com.tbc.agro.custo.controller;


import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import br.com.tbc.agro.custo.cases.CftProcessoUseCaseController;
import br.com.tbc.agro.custo.cases.dto.CftProcessoRequestSaveDTO;
import br.com.tbc.agro.custo.cases.dto.CftProcessoRequestUpdateDTO;
import br.com.tbc.agro.custo.cases.dto.CftProcessoResponseDTO;
import br.com.tbc.agro.custo.cases.dto.TipoProcessoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/cft-processos")
@Tag(name = "CFT Processos", description = "[ /cft-processos ] - API de Processos CFT.")
public class CftProcessoController {

    private final CftProcessoUseCaseController useCase;

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Buscar todos os processos")
    public ResponseEntity<List<CftProcessoResponseDTO>> buscarTodos() {
        log.info("c=CftProcessoController, m=buscarTodos");

        return useCase.selecionarTodos();
    }

    @GetMapping("/processos")
    @Operation(summary = "Buscar tipos de processos", description = "Buscar tipos de processos")
    public ResponseEntity<List<TipoProcessoResponseDTO>> buscarTiposProcesso() {
        log.info("c=CftProcessoController, m=buscarTiposProcesso");
        return useCase.selecionarTodosTiposProcesso();
    }


    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar novo processo")
    public ResponseEntity<CftProcessoResponseDTO> salvar(@RequestBody final CftProcessoRequestSaveDTO body) {
        log.info("c=CftProcessoController, m=salvar, body={}", body);

        return useCase.salvar(body);


    }

    @PutMapping("/{fgTpProcesso}")
    @Operation(summary = "Alterar", description = "Alterar processo")
    public ResponseEntity<CftProcessoResponseDTO> alterar(
            @PathVariable final TipoProcessoEnum fgTpProcesso,
            @RequestBody final CftProcessoRequestUpdateDTO body) {

        log.info("c=CftProcessoController, m=alterar, fgTpProcesso={}, body={}", fgTpProcesso, body);

        return useCase.alterarDataFim(fgTpProcesso, body.dtIniProcesso(), body.novaDtFimProcesso());
    }



    @DeleteMapping("/{fgTpProcesso}/{dtIniProcesso}")
    @Operation(summary = "Apagar", description = "Apagar processo")
    public ResponseEntity<CftProcessoResponseDTO> apagar(
            @PathVariable final TipoProcessoEnum fgTpProcesso,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") final LocalDate dtIniProcesso) {

        log.info("c=CftProcessoController, m=apagar, fgTpProcesso={}, dtIniProcesso={}", fgTpProcesso, dtIniProcesso);

        return useCase.apagar(fgTpProcesso, dtIniProcesso);
    }



    @GetMapping("/nao-processados")
    @Operation(
            summary = "Listar períodos para processamento",
            description = "Lista apenas períodos ainda não processados, ordenados do mais antigo para o mais recente"
    )
    public ResponseEntity<List<CftProcessoResponseDTO>> listarNaoProcessados() {
        return useCase.listarParaProcessamento();
    }

}
