package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResTopicDto {
    private Long id;
    private String title;
    private String description;
    private String email;
    private Boolean published;
}
