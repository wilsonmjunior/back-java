package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.CftProcesso;
import br.com.tbc.agro.core.domain.pimscs.vo.CftProcessoId;
import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record CftProcessoRequestSaveDTO(
        TipoProcessoEnum fgTpProcesso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dtIniProcesso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dtFimProcesso
) {
    public CftProcesso toEntity() {
        final CftProcesso entity = new CftProcesso();


        entity.setId(new CftProcessoId(fgTpProcesso, dtIniProcesso));
        entity.setDtFimProcesso(dtFimProcesso);

        return entity;
    }
}