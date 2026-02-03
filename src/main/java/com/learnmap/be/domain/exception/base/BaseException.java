package com.learnmap.be.domain.exception.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class BaseException extends RuntimeException {

    private final int statusCode;   // HTTP status
    private final String errorCode; // business error code

    protected BaseException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}

