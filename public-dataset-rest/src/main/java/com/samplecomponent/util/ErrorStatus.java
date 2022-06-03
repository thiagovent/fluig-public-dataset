package com.samplecomponent.util;

import java.io.Serializable;

public class ErrorStatus implements Serializable {

	private static final long serialVersionUID = 1L;

    private String message;

    private String exception;

    private String cause;

    public ErrorStatus(String message, String exception, String cause) {
        this.message = message;
        this.exception = exception;
        this.cause = cause;
    }

    public ErrorStatus(Throwable exception) {
        this.message = exception.getMessage();
        this.exception = String.valueOf(exception);
        this.cause = String.valueOf(exception.getCause());
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }
}
