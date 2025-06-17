package com.emrekentli.openremotecrud.exception;

public class OpenRemoteAuthException extends RuntimeException {
    public OpenRemoteAuthException(String message) {
        super(message);
    }
    public OpenRemoteAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
