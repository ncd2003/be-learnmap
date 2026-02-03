package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.type.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResResourceDto {
    private Long id;
    private String name;
    private String url;
    private ResourceType type;
    private Long size;
}
