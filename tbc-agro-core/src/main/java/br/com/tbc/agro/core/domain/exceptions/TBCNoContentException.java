package br.com.tbc.agro.core.domain.exceptions;

import br.com.tbc.agro.core.domain.dto.MessageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TBCNoContentException extends RuntimeException {
    private final MessageDTO error;

    public TBCNoContentException(final MessageDTO message) {
        this.error = message;
    }
}
