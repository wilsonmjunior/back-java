package br.com.tbc.agro.custo.controller;

import br.com.tbc.agro.core.domain.dto.ReportDTO;
import br.com.tbc.agro.custo.cases.ReportUseCase;
import br.com.tbc.agro.custo.cases.dto.enums.ExportFormatEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@Tag(name = "Relatório", description = "[ /report ] - API de exportação de relatório.")
public class ReportController {

    private final ReportUseCase reportUseCase;

    @PostMapping("/export")
    public ResponseEntity<byte[]> export(
            @RequestBody final ReportDTO jsonData,
            @RequestParam(value = "TYPE", defaultValue = "PDF") final String mediaType,
            @RequestParam(value = "IS_SUMMARY", defaultValue = "false") final Boolean isSummary
    ) throws Exception {
        final byte[] exportFile = reportUseCase.generateReport(
                jsonData,
                mediaType,
                isSummary
        );
        final ExportFormatEnum exportFormatEnum = ExportFormatEnum.fromValue(mediaType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(exportFormatEnum.getContentType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=relatorio\"" + LocalDateTime.now() + "." + exportFormatEnum.name().toLowerCase(
                                Locale.ROOT)
                )
                .body(exportFile);
    }
}
