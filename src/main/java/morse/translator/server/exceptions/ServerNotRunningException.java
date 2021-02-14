package morse.translator.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerNotRunningException extends RuntimeException {
    public ServerNotRunningException(String message) { super(message); }

    public ServerNotRunningException(String message, Throwable cause) { super(message, cause); }
}
