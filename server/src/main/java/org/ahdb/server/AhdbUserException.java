package org.ahdb.server;

public class AhdbUserException extends RuntimeException {
    public AhdbUserException() {
        super();
    }

    public AhdbUserException(String message) {
        super(message);
    }

    public AhdbUserException(String message, Exception cause) {
        super(message, cause);
    }

    public AhdbUserException(Exception cause) {
        super(cause);
    }
}
