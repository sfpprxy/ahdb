package org.ahdb.common;

public class AhdbUserException extends RuntimeException {

    public static final String NO_POWER = "not enough power!";
    public static final String NO_FREE = "not enough free chance today!";
    public static final String NO_ITEM = "no such item";

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
