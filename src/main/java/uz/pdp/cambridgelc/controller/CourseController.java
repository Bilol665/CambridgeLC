package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.service.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/admin/add-course")
    public CourseEntity addCourse(
            @RequestBody CourseDto courseDto
    ) {
        return courseService.addCourse(courseDto);
    }
    @DeleteMapping("/admin/delete-course")
    public void deleteCourse(
            @RequestParam String title
    ){
        courseService.deleteByTitle(title);
    }
    @GetMapping("/getAll")
    public List<CourseEntity> getAll(
            @RequestParam int page,
            @RequestParam int size
    ){
        return courseService.getAll(page, size);
    }
    @PostMapping("/support/edit-course")
    public CourseEntity editCourse(
            @PathVariable UUID id,
            @RequestBody CourseDto courseDto
    ){
        return courseService.updateSupport(courseDto,id);
    }
    @PostMapping("/teacher/edit-title")
    public CourseEntity editTitle(
            @PathVariable UUID courseId,
            @RequestParam String title
    ){
        return courseService.updateTeacher(title,courseId);
    }
}
