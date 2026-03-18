package br.com.tbc.agro.custo.cases;

import br.com.tbc.agro.core.domain.pimscs.repository.CftHistoricoRepository;
import br.com.tbc.agro.core.domain.pimscs.repository.CftProcessoRepository;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.FgProcessadoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReversaoCustoFrenteUseCase {

    private final CftHistoricoRepository historicoRepository;
    private final CftProcessoRepository processoRepository;

    @Transactional
    public void reverterCustoFrente(final CftProcessoId idPeriodo) {
        // Lista vazia = todas as frentes
        final List<Long> todasFrentes = List.of();

        // Apaga histórico do período
//        int registrosApagados = historicoRepository.excluirPeriodoPorFrentes(
//                idPeriodo.getDtIniProcesso(),
//                todasFrentes
//        );
//        log.info("Registros apagados: {}", registrosApagados);

        // Atualiza status do período
        final CftProcesso processo = processoRepository.findById(idPeriodo)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        processo.setFgProcessado(FgProcessadoEnum.N);
        processoRepository.save(processo);

        log.info("Período {} marcado como não processado", idPeriodo);
    }


    public CftProcessoId obterUltimoPeriodoProcessado() {

        return processoRepository.findAll()
                .stream()
                .filter(p -> p.getFgProcessado() == FgProcessadoEnum.S)
                .max(Comparator.comparing(p -> p.getId().getDtIniProcesso()))
                .map(CftProcesso::getId)
                .orElseThrow(() -> new IllegalStateException("Nenhum período processado encontrado"));
    }
}
