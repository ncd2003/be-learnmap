package com.learnmap.be.domain.exception.base;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {

    private int statusCode;
    private String error;    // errorCode
    private Object message;  // string hoặc list
    private T data;

    public static <T> RestResponse<T> success(
            int statusCode,
            String message,
            T data
    ) {
        return new RestResponse<>(statusCode, null, message, data);
    }

    public static <T> RestResponse<T> error(
            int statusCode,
            String error,
            Object message
    ) {
        return new RestResponse<>(statusCode, error, message, null);
    }
}

