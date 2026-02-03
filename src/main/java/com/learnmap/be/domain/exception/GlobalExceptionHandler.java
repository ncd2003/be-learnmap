package com.learnmap.be.domain.exception;

import com.learnmap.be.domain.exception.base.BaseException;
import com.learnmap.be.domain.exception.base.ErrorCode;
import com.learnmap.be.domain.exception.base.RestResponse;
import com.learnmap.be.domain.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RestResponse<Object>> handleBaseException(
            BaseException ex
    ) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        RestResponse.error(
                                ex.getStatusCode(),
                                ex.getErrorCode(),
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest()
                .body(
                        RestResponse.error(
                                400,
                                ErrorCode.NOT_VALID,
                                errors
                        )
                );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestResponse<Object>> handleAuth(
            AuthenticationException ex
    ) {
        // Ưu tiên message từ cause, sau đó từ exception, cuối cùng mới dùng default
        String message = Optional.ofNullable(ex.getCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage() != null ? ex.getMessage() : Constants.INVALID_TOKEN);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        RestResponse.error(
                                401,
                                ErrorCode.UNAUTHORIZED,
                                message
                        )
                );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<RestResponse<Object>> handleHttpClientError(
            HttpClientErrorException ex
    ) {
        log.error("HTTP Client error: {} - {}", ex.getStatusCode(), ex.getMessage());
        
        String message = "Lỗi kết nối tới dịch vụ AI bên ngoài";
        String errorCode = "EXTERNAL_SERVICE_ERROR";
        
        if (ex instanceof HttpClientErrorException.TooManyRequests) {
            message = "Hệ thống đang quá tải. Vui lòng thử lại sau.";
            errorCode = ErrorCode.RATE_LIMIT_EXCEEDED;
        }
        
        return ResponseEntity.status(ex.getStatusCode())
                .body(
                        RestResponse.error(
                                ex.getStatusCode().value(),
                                errorCode,
                                message
                        )
                );
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<RestResponse<Object>> handleRestClientError(
            RestClientException ex
    ) {
        log.error("REST Client error: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        RestResponse.error(
                                503,
                                "SERVICE_UNAVAILABLE",
                                "Không thể kết nối tới dịch vụ. Vui lòng thử lại sau."
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleUnknown(
            Exception ex
    ) {
        log.error("Unhandled exception", ex);

        return ResponseEntity.internalServerError()
                .body(
                        RestResponse.error(
                                500,
                                "INTERNAL_ERROR",
                                Constants.INTERNAL_SERVER_ERROR
                        )
                );
    }
}
