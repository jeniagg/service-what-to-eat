package what.to.eat.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.WebApplicationException;

@ControllerAdvice
public class WebApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(WebApplicationExceptionHandler.class);

    @ExceptionHandler(WebApplicationException.class)
    public final ResponseEntity<Object> handleAllExceptions(WebApplicationException ex) {
        LOGGER.error("WebApplicationError occured: " + ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
