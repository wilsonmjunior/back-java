package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.pimscs.service.CftProcessoService;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import br.com.tbc.agro.custo.cases.dto.CftProcessoRequestSaveDTO;
import br.com.tbc.agro.custo.cases.dto.CftProcessoResponseDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.tbc.agro.custo.cases.dto.TipoProcessoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class CftProcessoUseCaseController {

    private final CftProcessoService service;


    public ResponseEntity<List<CftProcessoResponseDTO>> selecionarTodos() {
        final var data = service.selecionarTodos();
        final var response = new ArrayList<CftProcessoResponseDTO>();

        for (CftProcesso processo : data) {
            response.add(CftProcessoResponseDTO.from(processo));
        }

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<CftProcessoResponseDTO> salvar(final CftProcessoRequestSaveDTO dto) {
        final CftProcesso entity = dto.toEntity();
        final CftProcesso data = service.salvar(entity);

        return new ResponseEntity<>(CftProcessoResponseDTO.from(data), CREATED);
    }


    public ResponseEntity<CftProcessoResponseDTO> alterarDataFim(
            final TipoProcessoEnum fgTpProcesso,
            final LocalDate dtIniProcesso,
            final LocalDate novaDtFimProcesso
    ) {


        final CftProcessoId id = new CftProcessoId(fgTpProcesso, dtIniProcesso);
        final CftProcesso data = service.alterarDataFim(id, novaDtFimProcesso);

        return new ResponseEntity<>(CftProcessoResponseDTO.from(data), OK);
    }

    public ResponseEntity<CftProcessoResponseDTO> apagar(
            final TipoProcessoEnum fgTpProcesso,
            final LocalDate dtIniProcesso
    ) {

        final CftProcessoId id = new CftProcessoId(fgTpProcesso, dtIniProcesso);
        final CftProcesso data = service.apagar(id);

        return new ResponseEntity<>(CftProcessoResponseDTO.from(data), OK);
    }

    public ResponseEntity<List<CftProcessoResponseDTO>> listarParaProcessamento() {

        final var processos = service.listarNaoProcessados();
        final var response = processos.stream()
                .map(CftProcessoResponseDTO::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<TipoProcessoResponseDTO>> selecionarTodosTiposProcesso() {
        final List<TipoProcessoResponseDTO> response = Arrays
                .stream(
                        TipoProcessoEnum.values())
                .map(item ->
                        new TipoProcessoResponseDTO(item.name(), item.getDescricao())
                ).toList();

        return ResponseEntity.ok(response);
    }
}
