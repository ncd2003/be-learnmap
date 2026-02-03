package com.learnmap.be.domain.exception;

import com.learnmap.be.domain.exception.base.BaseException;
import com.learnmap.be.domain.exception.base.ErrorCode;

public class HuggingFaceRateLimitException extends BaseException {

    public HuggingFaceRateLimitException(String message) {
        super(message, 429, ErrorCode.RATE_LIMIT_EXCEEDED);
    }
}
