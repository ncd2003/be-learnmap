package com.learnmap.be.domain.service;

import com.learnmap.be.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService {
    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected CourseRepository courseRepository;
    @Autowired
    protected LearningPathRepository learningPathRepository;
    @Autowired
    protected ChapterRepository chapterRepository;
    @Autowired
    protected LessonRepository lessonRepository;
    @Autowired
    protected ResourceRepository resourceRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected TopicRepository topicRepository;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected PlanRepository planRepository;
    @Autowired
    protected FeatureRepository featureRepository;
    @Autowired
    protected SubscriptionRepository subscriptionRepository;
    @Autowired
    protected CareerQuestionRepository careerQuestionRepository;
}
