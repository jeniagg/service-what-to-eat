package what.to.eat.exception;

import org.springframework.http.HttpStatus;

public class WebApplicationException extends RuntimeException {

    private final HttpStatus status;

    public WebApplicationException(String message, HttpStatus status) {
       super(message);
       this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
