package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record CftProcessoResponseDTO(
        String fgTpProcesso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dtIniProcesso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dtFimProcesso,
        String fgProcessado
) {

    public static CftProcessoResponseDTO from(final CftProcesso entity) {

        return new CftProcessoResponseDTO(
                entity.getId().getFgTpProcesso().getDescricao(),
                entity.getId().getDtIniProcesso(),
                entity.getDtFimProcesso(),
                entity.getFgProcessado().getDescricao()
        );
    }
}
