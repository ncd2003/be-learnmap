package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqLessonDto;
import com.learnmap.be.domain.entities.Chapter;
import com.learnmap.be.domain.entities.Lesson;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonService extends BaseService {
    private final MapperUtils mapperUtils;

    public Lesson createLesson(ReqLessonDto reqLessonDto) {
        Lesson lesson = mapperUtils.convertObjectToObject(reqLessonDto, Lesson.class);
        lesson.setId(null);
        if (lessonRepository.existsByChapter_IdAndPositionAndActiveTrue(reqLessonDto.getChapterId(), reqLessonDto.getPosition())) {
            throw new BadRequestException(Constants.POSITION_DUPLICATED);
        }
        Chapter chapterDB = chapterRepository.findById(reqLessonDto.getChapterId()).orElseThrow(() -> new BadRequestException(Constants.CHAPTER_NOT_FOUND));
        lesson.setChapter(chapterDB);
        lessonRepository.save(lesson);
        return lesson;
    }
}
