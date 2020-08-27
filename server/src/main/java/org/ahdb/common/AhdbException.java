package org.ahdb.common;

public class AhdbException extends RuntimeException {

    public AhdbException() {
        super();
    }

    public AhdbException(String message) {
        super(message);
    }

    public AhdbException(String message, Exception cause) {
        super(message, cause);
    }

    public AhdbException(Exception cause) {
        super(cause);
    }

}
