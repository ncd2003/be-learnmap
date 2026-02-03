package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqTopicDto;
import com.learnmap.be.domain.dto.ResTopicDto;
import com.learnmap.be.domain.entities.Topic;
import com.learnmap.be.domain.utils.JWTUtils;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicService extends BaseService {

    private final MapperUtils mapperUtils;

    public List<ResTopicDto> getTopics() {
        return topicRepository.findAllByActiveTrue().stream().map(t -> mapperUtils.convertObjectToObject(t, ResTopicDto.class)).toList();
    }

    public Topic createTopic(ReqTopicDto reqTopicDto) {
        Topic topic = mapperUtils.convertObjectToObject(reqTopicDto, Topic.class);
        topic.setId(null);
        String email = JWTUtils.getCurrentUserLogin().isPresent() ? JWTUtils.getCurrentUserLogin().get() : null;
        topic.setAccount(email);
        topicRepository.save(topic);
        return topic;
    }
}
