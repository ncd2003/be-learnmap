package com.learnmap.be.domain.exception;

import com.learnmap.be.domain.exception.base.BaseException;
import com.learnmap.be.domain.exception.base.ErrorCode;

public class WrongUserOrPasswordException extends BaseException {
    public WrongUserOrPasswordException(String message) {
        super(
                message,
                401,
                ErrorCode.UNAUTHORIZED
        );
    }
}
