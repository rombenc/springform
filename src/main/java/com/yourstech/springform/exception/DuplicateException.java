package com.yourstech.springform.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends RuntimeException {
    private HttpStatus status;

    public DuplicateException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
