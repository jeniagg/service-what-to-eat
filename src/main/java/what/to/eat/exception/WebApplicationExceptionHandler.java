package what.to.eat.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WebApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(WebApplicationExceptionHandler.class);

    @ExceptionHandler(WebApplicationException.class)
    public final ResponseEntity<Object> handleAllExceptions(WebApplicationException ex) {

        LOG.error("WebApplicationError occurred {}", ex.getMessage());

        WebApplicationExceptionFormat exceptionResponse =
                new WebApplicationExceptionFormat(ex.getMessage(), ex.getStatus());

        return new ResponseEntity<>(exceptionResponse, ex.getStatus());

    }
}
