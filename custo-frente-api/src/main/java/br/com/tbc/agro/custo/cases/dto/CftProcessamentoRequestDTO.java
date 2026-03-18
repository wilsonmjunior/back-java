package br.com.tbc.agro.custo.cases.dto;

import br.com.tbc.agro.core.domain.pimscs.vo.TipoProcessoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record CftProcessamentoRequestDTO(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dtIniProcesso,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dtFimProcesso,

        List<Long> frentesTrabalho,

        TipoProcessoEnum tipoProcesso
) { }