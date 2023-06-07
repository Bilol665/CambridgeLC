package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.service.CourseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/")
public class CourseController {
    private final CourseService courseService;
@PostMapping("/admin/add-course")
    public CourseEntity addCourse(
        @RequestBody CourseDto courseDto
        ){
    return courseService.addCourse(courseDto);
}
}
