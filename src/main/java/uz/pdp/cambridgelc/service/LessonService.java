package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.LessonDto;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.lesson.LessonEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.NotEnoughCreditsException;
import uz.pdp.cambridgelc.repository.CourseRepository;
import uz.pdp.cambridgelc.repository.LessonRepository;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public LessonEntity save(LessonDto lessonDto, UUID courseId) {
        LessonEntity lesson = modelMapper.map(lessonDto, LessonEntity.class);
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(
                () -> new DataNotFoundException("Course not found!")
        );
        try {
            lesson.setDuration(Duration.ofDays(lessonDto.getDuration()));
        }catch (Exception e) {
            throw new NotEnoughCreditsException("Failed to convert duration!");
        }
        List<LessonEntity> lessons = course.getLessons();
        lessons.add(lesson);
        course.setLessons(lessons);
        courseRepository.save(course);
        return lesson;
    }
}
