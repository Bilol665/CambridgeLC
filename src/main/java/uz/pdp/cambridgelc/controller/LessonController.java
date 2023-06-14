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
import uz.pdp.cambridgelc.service.LessonService;
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
            @Valid @PathVariable UUID courseId,
            @Valid @RequestBody LessonDto lessonDto,
            BindingResult bindingResult
    ) {
        return new ResponseEntity<>(lessonService.save(lessonDto, courseId, bindingResult), HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<HttpStatus> cancel(
            @Valid @PathVariable UUID id,
            BindingResult bindingResult
    ) {
        lessonService.cancel(id, bindingResult);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PutMapping("changeTopic/{id}")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<Object> changeTopic(
            @Valid @PathVariable UUID id,
            @Valid @RequestParam String theme,
            BindingResult bindingResult
    ) {
        return new ResponseEntity<>(lessonService.changeTopic(id, theme, bindingResult),HttpStatus.OK);
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
