package com.learnmap.be.domain.exception;

import com.learnmap.be.domain.exception.base.BaseException;
import com.learnmap.be.domain.exception.base.ErrorCode;

public class ForBiddenException extends BaseException {
    public ForBiddenException(String message) {
        super(message, 403, ErrorCode.FORBIDDEN);
    }
}
