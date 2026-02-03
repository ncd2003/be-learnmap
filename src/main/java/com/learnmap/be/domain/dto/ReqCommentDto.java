package com.learnmap.be.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCommentDto {
    private String content;
    private Long postId;
}
