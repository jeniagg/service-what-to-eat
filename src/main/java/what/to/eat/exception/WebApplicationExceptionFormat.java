package what.to.eat.exception;

import org.springframework.http.HttpStatus;

public class WebApplicationExceptionFormat {

    private String message;
    private HttpStatus status;

    protected WebApplicationExceptionFormat() {
    }

    public WebApplicationExceptionFormat(
            String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
