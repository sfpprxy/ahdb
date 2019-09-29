package org.jwork.ahdb;

public class AhdbException extends RuntimeException {
    public AhdbException() {
        super();
    }

    public AhdbException(String message) {
        super(message);
    }

    public AhdbException(String message, Throwable cause) {
        super(message, cause);
    }

    public AhdbException(Throwable cause) {
        super(cause);
    }
}
