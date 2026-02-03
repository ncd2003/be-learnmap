package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqResourceDto;
import com.learnmap.be.domain.dto.ResResourceDto;
import com.learnmap.be.domain.entities.Lesson;
import com.learnmap.be.domain.entities.Resource;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResourceService extends BaseService {
    private final MapperUtils mapperUtils;

    @Value("${aws.s3.prefix-url}")
    private String prefixURL;

    public ResourceService(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    public ResResourceDto getResourceById(Long id) {
        Resource resourceDB = resourceRepository.findResourceById(id).orElseThrow(() -> new BadRequestException(Constants.RESOURCE_NOT_FOUND));
        return mapperUtils.convertObjectToObject(resourceDB, ResResourceDto.class);
    }

    public Resource createResource(ReqResourceDto reqResourceDto) {
        Resource resource = mapperUtils.convertObjectToObject(reqResourceDto, Resource.class);
        resource.setId(null);
        resource.setUrl(prefixURL + "/" + resource.getUrl());
        Lesson lessonDB = lessonRepository.findLessonById(reqResourceDto.getLessonId()).orElseThrow(() -> new BadRequestException(Constants.LESSON_NOT_FOUND));
        resource.setLesson(lessonDB);
        resourceRepository.save(resource);
        return resource;
    }
}
