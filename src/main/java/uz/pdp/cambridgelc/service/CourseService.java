package uz.pdp.cambridgelc.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.course.CourseLevel;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.entity.exam.TaskEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.repository.CourseRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    public CourseEntity addCourse(CourseDto courseDto){
        CourseEntity course = modelMapper.map(courseDto, CourseEntity.class);
        try {
            course.setLevel(CourseLevel.valueOf(courseDto.getLevel()));
            return courseRepository.save(course);
        }catch (Exception e){
            throw new DataNotFoundException("Course level not found");
        }
    }
    public void deleteByTitle(String title) {
        courseRepository.removeCourseEntityByTitle(title);
    }
    public List<CourseEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("createdDate").descending());
        return courseRepository.findAll(pageable).getContent();
    }
    public CourseEntity updateSupport(CourseDto courseDto, UUID id){
        CourseEntity course = courseRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Course not found!")
        );
        modelMapper.map(courseDto,course);
        return courseRepository.save(course);
    }
    public CourseEntity updateTeacher(String title,UUID id){
        CourseEntity course=courseRepository.findById(id).orElseThrow(
               () -> new DataNotFoundException("Course not found")
       );
        course.setTitle(title);
        return courseRepository.save(course);
    }

}
