package br.com.tbc.agro.custo.util;

import static br.com.tbc.agro.core.domain.dto.TypeMessage.ERROR;
import br.com.tbc.agro.core.domain.dto.MessageDTO;
import br.com.tbc.agro.core.domain.exceptions.TBCNoContentException;
import br.com.tbc.agro.core.domain.exceptions.TBCValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class TBCExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TBCValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageDTO> handleException(final TBCValidationException ex, final HttpServletRequest request) {

        return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TBCNoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<MessageDTO> handleException(final TBCNoContentException ex, final HttpServletRequest request) {

        return new ResponseEntity<>(ex.getError(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageDTO> handleGenericException(final Exception ex, final HttpServletRequest request, final HttpServletResponse response) {
        final var error = new MessageDTO(ERROR, ex.getMessage());
        log.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageDTO> handleRuntimeException(final RuntimeException ex, final HttpServletRequest request, final HttpServletResponse response) {
        final var error = new MessageDTO(ERROR, ex.getMessage());
        log.error(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
