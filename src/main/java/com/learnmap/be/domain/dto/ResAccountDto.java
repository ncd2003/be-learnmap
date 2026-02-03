package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.Role;
import com.learnmap.be.domain.entities.type.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAccountDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private UserStatus status;
    private Role role;
}
