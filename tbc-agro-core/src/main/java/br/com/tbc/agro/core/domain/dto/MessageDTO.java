package br.com.tbc.agro.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class MessageDTO {
    private TypeMessage type;
    private String description;
    private List<MessageDTO> failures;
    private LocalDateTime dataHoraIntegracao;

    public MessageDTO(final String description) {
        this.description = description;
        this.dataHoraIntegracao = LocalDateTime.now();
    }

    public MessageDTO(final TypeMessage tipoMensagem, final String mensagem) {
        this.type = tipoMensagem;
        this.description = mensagem;
        this.dataHoraIntegracao = LocalDateTime.now();
    }

    public MessageDTO(final TypeMessage tipoMensagem, final String mensagem, final List<MessageDTO> failures) {
        this.type = tipoMensagem;
        this.description = mensagem;
        this.failures = failures;
        this.dataHoraIntegracao = LocalDateTime.now();
    }
}