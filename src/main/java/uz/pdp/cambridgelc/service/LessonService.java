package uz.pdp.cambridgelc.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.LessonDto;
import uz.pdp.cambridgelc.entity.lesson.LessonEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.NotEnoughCreditsException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
import uz.pdp.cambridgelc.repository.CourseRepository;
import uz.pdp.cambridgelc.repository.LessonRepository;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public LessonEntity save(LessonDto lessonDto, UUID courseId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new RequestValidationException(errors);
        }
        LessonEntity lesson = modelMapper.map(lessonDto, LessonEntity.class);
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(
                () -> new DataNotFoundException("Course not found!")
        );
        try {
            lesson.setDuration(Duration.ofDays(lessonDto.getDuration()));
        } catch (Exception e) {
            throw new NotEnoughCreditsException("Failed to convert duration!");
        }
        List<LessonEntity> lessons = course.getLessons();
        lessons.add(lesson);
        course.setLessons(lessons);
        courseRepository.save(course);
        return lessonRepository.findLessonEntitiesByTheme(lessonDto.getTheme()).get(0);
    }

    public void cancel(UUID id) {
        LessonEntity lessonEntity = lessonRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("lesson not found!")
        );
        List<CourseEntity> all = courseRepository.findAll();
        for(CourseEntity c:all) {
            List<LessonEntity> lessons = c.getLessons();
            lessons.remove(lessonEntity);
            c.setLessons(lessons);
            courseRepository.save(c);
        }
        lessonRepository.removeLessonEntityById(id);
    }
    public LessonEntity changeTopic(UUID id, String theme) {
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Theme not found!")
        );
        lesson.setTheme(theme);
        return lessonRepository.save(lesson);
    }
    public List<LessonEntity> getAll(int page,int size){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);
        return lessonRepository.findAll(pageable).getContent();
    }
}
