package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.LessonDto;
import uz.pdp.cambridgelc.service.LessonService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;
    @PostMapping("/add-lesson/{courseId}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Object> addLesson(
            @PathVariable UUID courseId,
            @RequestBody LessonDto lessonDto
    ){
        return new ResponseEntity<>(lessonService.save(lessonDto,courseId), HttpStatus.OK);
    }

}
