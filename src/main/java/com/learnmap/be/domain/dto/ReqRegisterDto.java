package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ReqRegisterDto {
    @NotBlank
    @Email
    @Length(min = 6, max = 50)
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Length(max = 80)
    private String fullName;

    @NotBlank
    private String password;
    private String confirmPassword;

}
