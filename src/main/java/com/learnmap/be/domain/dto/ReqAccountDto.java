package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.type.RoleName;
import com.learnmap.be.domain.entities.type.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqAccountDto {
    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    private UserStatus status;
    private RoleName role;
}
