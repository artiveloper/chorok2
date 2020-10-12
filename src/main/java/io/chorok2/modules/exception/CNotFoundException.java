package io.chorok2.modules.exception;

public class CNotFoundException extends RuntimeException {

    public CNotFoundException() {
        super();
    }

    public CNotFoundException(String message) {
        super(message);
    }

    public CNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
