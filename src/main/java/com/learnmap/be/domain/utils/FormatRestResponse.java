package com.learnmap.be.domain.utils;

import com.learnmap.be.domain.exception.base.RestResponse;
import com.learnmap.be.domain.utils.annotation.APIMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice {
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        return true;
//    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getContainingClass().getPackageName().contains("swagger");
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int statusCode = servletResponse.getStatus();

        RestResponse<Object> restResponse = new RestResponse<>();
        if(body instanceof String || body instanceof Resource || body instanceof RestResponse<?>) {
            return body;
        }
        // Error code >= 400
        if (statusCode >= 400) {
            return body;
        }
        // Success code (200 -> 399)
        else {
            restResponse.setStatusCode(statusCode);
            restResponse.setData(body);
            APIMessage apiMessage = returnType.getMethodAnnotation(APIMessage.class);
            restResponse.setMessage(apiMessage != null ? apiMessage.value() : "Call API Successfully");
        }
        return restResponse;
    }
}
