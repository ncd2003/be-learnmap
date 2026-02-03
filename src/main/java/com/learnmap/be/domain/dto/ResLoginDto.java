package com.learnmap.be.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.learnmap.be.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResLoginDto {
    @JsonProperty("access_token")
    private String accessToken;
    private AccountLogin userLogin;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountLogin {
        private Long accountId;
        private String email;
        private String fullName;
        private String role;
        private String plan;
    }
}
