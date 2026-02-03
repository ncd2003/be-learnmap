package com.learnmap.be.domain.exception;

import com.learnmap.be.domain.exception.base.BaseException;
import com.learnmap.be.domain.exception.base.ErrorCode;

public class HuggingFaceApiException extends BaseException {

    public HuggingFaceApiException(String message) {
        super(message, 503, ErrorCode.AI_SERVICE_ERROR);
    }
}
