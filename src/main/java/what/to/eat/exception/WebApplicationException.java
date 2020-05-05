package what.to.eat.exception;

import org.springframework.http.HttpStatus;

public class WebApplicationException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public WebApplicationException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}