package com.study.exceptionhandler;

public class TodoInfoDataException extends RuntimeException {
    private final String message;

    public TodoInfoDataException(final String errorMessages) {
        super(errorMessages);
        this.message = errorMessages;
    }
}
