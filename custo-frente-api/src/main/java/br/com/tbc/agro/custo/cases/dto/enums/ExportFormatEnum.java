package br.com.tbc.agro.custo.cases.dto.enums;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum ExportFormatEnum {
    PDF("application/pdf"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final String contentType;
    ExportFormatEnum(final String contentType) {
        this.contentType = contentType;
    }

    public static ExportFormatEnum fromValue(final String value) {
        try {
            return ExportFormatEnum.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (Exception exception) {
            return PDF;
        }
    }
}
