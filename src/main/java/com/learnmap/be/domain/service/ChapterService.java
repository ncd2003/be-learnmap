package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqChapterDto;
import com.learnmap.be.domain.entities.Chapter;
import com.learnmap.be.domain.entities.LearningPath;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChapterService extends BaseService {
    private MapperUtils mapperUtils;

    public Chapter createChapter(ReqChapterDto reqChapterDto) {
        Chapter chapter = mapperUtils.convertObjectToObject(reqChapterDto, Chapter.class);
        chapter.setId(null);
        if (chapterRepository.existsByLearningPath_IdAndPositionAndActiveTrue(reqChapterDto.getLearningPathId(), reqChapterDto.getPosition())) {
            throw new BadRequestException(Constants.POSITION_DUPLICATED);
        }
        LearningPath learningPathDB = learningPathRepository.findById(reqChapterDto.getLearningPathId()).orElseThrow(() -> new BadRequestException(Constants.LEARNING_PATH_NOT_FOUND));
        chapter.setLearningPath(learningPathDB);
        chapterRepository.save(chapter);
        return chapter;
    }


}
