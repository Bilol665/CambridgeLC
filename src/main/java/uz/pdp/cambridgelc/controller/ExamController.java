package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.ExamDto;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;
import uz.pdp.cambridgelc.service.exam.ExamService;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {
    private final ExamService examService;

    @PostMapping("/add")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<ExamEntity> addExam(
            @Valid @RequestBody ExamDto examDto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(examService.add(examDto,bindingResult));
    }
    @PutMapping("/start/{examId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Object> start(
            @PathVariable UUID examId
    ) {
        return new ResponseEntity<>(examService.start(examId), HttpStatus.OK);
    }

}
