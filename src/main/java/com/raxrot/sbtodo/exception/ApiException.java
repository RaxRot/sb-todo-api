package com.raxrot.sbtodo.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
