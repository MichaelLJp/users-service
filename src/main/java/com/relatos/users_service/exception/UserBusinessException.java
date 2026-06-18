package com.relatos.users_service.exception;

import org.springframework.http.HttpStatus;

public class UserBusinessException extends RuntimeException {

    private final HttpStatus status;

    public UserBusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public UserBusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
