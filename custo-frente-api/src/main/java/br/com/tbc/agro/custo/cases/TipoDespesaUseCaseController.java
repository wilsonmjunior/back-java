package br.com.tbc.agro.custo.cases;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import br.com.tbc.agro.core.domain.pimscs.service.TipoDespesaService;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoDespesa;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaDTO;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaRequestSaveDTO;
import br.com.tbc.agro.custo.cases.dto.TipoDespesaRequestUpdateDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoDespesaUseCaseController {

    private final TipoDespesaService service;

    public ResponseEntity<List<TipoDespesaDTO>> selecionarTodos() {
        final var data = service.selecionarTodos();
        final var response = new ArrayList<TipoDespesaDTO>();

        for (TipoDespesa tipoDespesa : data) {
            response.add(TipoDespesaDTO.from(tipoDespesa));
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<TipoDespesaDTO> buscarPorCodigo(final Long codigo) {
        final var data = service.buscarPorCodigo(codigo);

        return ResponseEntity.ok(TipoDespesaDTO.from(data));
    }

    public ResponseEntity<TipoDespesaDTO> salvar(final TipoDespesaRequestSaveDTO dto) {
        final var data = service.salvar(dto.toModel(), dto.contas());

        return new ResponseEntity<>(TipoDespesaDTO.from(data), CREATED);
    }

    public ResponseEntity<TipoDespesaDTO> alterar(final Long codigo, final TipoDespesaRequestUpdateDTO body) {

        final var request = new TipoDespesa();
        request.setCodigo(codigo);
        request.setDescricao(body.descricao());

        final var data = service.alterar(request);

        return new ResponseEntity<>(TipoDespesaDTO.from(data), OK);
    }

    public ResponseEntity<TipoDespesaDTO> apagar(final Long codigo) {
        final var data = service.apagar(codigo);

        return new ResponseEntity<>(TipoDespesaDTO.from(data), OK);
    }

    public ResponseEntity<TipoDespesaDTO> adicionarConta(final Long codigo, final String codigoConta) {
        final var data = service.adicionarConta(codigo, codigoConta);

        return new ResponseEntity<>(TipoDespesaDTO.from(data), OK);
    }

    public ResponseEntity<TipoDespesaDTO> removerConta(final Long codigo, final String codigoConta) {
        final var data = service.removerConta(codigo, codigoConta);

        return new ResponseEntity<>(TipoDespesaDTO.from(data), OK);
    }
}
