package com.study.exceptionhandler;

public class TodoInfoNotFoundException extends RuntimeException{
    private String message;
    private Throwable ex;

    public TodoInfoNotFoundException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public TodoInfoNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
