package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReqSubscriptionDto {
    @NotNull(message = "Tài khoản không được trống")
    private Long accountId;
    @NotNull(message = "Gói đăng ký không được trống")
    private Long planId;
}
