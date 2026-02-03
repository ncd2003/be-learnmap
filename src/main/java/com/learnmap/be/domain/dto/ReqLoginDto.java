package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqLoginDto {
    @Email
    @NotBlank(message = "email không được để trống")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
