package uz.pdp.cambridgelc.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.course.CourseLevel;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.exceptions.NotCourseException;
import uz.pdp.cambridgelc.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public CourseEntity addCourse(CourseDto courseDto){
        CourseEntity course = modelMapper.map(courseDto, CourseEntity.class);
        try {
            course.setLevel(CourseLevel.valueOf(courseDto.getLevel()));
            return courseRepository.save(course);
        }catch (Exception e){
            throw new NotCourseException("Course level not found");
        }
    }
    public void deleteByTitle(String title) {
        courseRepository.deleteCourseEntityByTitle(title);
    }
    public List<CourseEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable).getContent();
    }
}
