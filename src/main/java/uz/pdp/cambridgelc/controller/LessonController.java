package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.LessonDto;
import uz.pdp.cambridgelc.entity.lesson.LessonEntity;
import uz.pdp.cambridgelc.service.lesson.LessonService;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;
    @PostMapping("/add/{courseId}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> addLesson(
            @PathVariable UUID courseId,
            @Valid @RequestBody LessonDto lessonDto,
            BindingResult bindingResult
    ) {
        return new ResponseEntity<>(lessonService.save(lessonDto, courseId, bindingResult), HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<HttpStatus> cancel(
            @PathVariable UUID id
    ) {
        lessonService.cancel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/changeTopic/{id}")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<Object> changeTopic(
            @PathVariable UUID id,
            @RequestParam(required = false,defaultValue = "") String theme
    ) {
        return new ResponseEntity<>(lessonService.changeTopic(id, theme),HttpStatus.OK);
    }
    @GetMapping("/getAll")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<LessonEntity>> getAll(
             @RequestParam(required = false,defaultValue = "0") int page,
             @RequestParam(required = false,defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(lessonService.getAll(page, size));
    }
}
