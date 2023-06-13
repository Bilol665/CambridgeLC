package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.course.CourseEntity;
import uz.pdp.cambridgelc.entity.dto.CourseDto;
import uz.pdp.cambridgelc.service.course.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<CourseEntity> addCourse(
            @Valid @RequestBody CourseDto courseDto,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(courseService.addCourse(courseDto, bindingResult));
    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteCourse(
            @Valid @RequestParam String title,
            BindingResult bindingResult

    ) {
        courseService.deleteByTitle(title, bindingResult);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<CourseEntity>> getAll(
            @Valid @RequestParam int page,
            @Valid @RequestParam int size,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(courseService.getToExcel(page, size, bindingResult));
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<CourseEntity> editCourse(
            @Valid @PathVariable UUID id,
            @Valid @RequestBody CourseDto courseDto,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(courseService.updateSupport(courseDto, id, bindingResult));
    }

    @PutMapping("/edit-title/{courseId}")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<CourseEntity> editTitle(
            @Valid @PathVariable UUID courseId,
            @Valid @RequestParam String title,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(courseService.updateTeacher(title, courseId, bindingResult));
    }

    @GetMapping("/getCoursesByLevel")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<CourseEntity>> getCoursesByLevel(
            @Valid @RequestParam String level,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(courseService.getCoursesByLevel(level, bindingResult));
    }
}
