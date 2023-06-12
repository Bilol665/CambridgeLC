package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cambridgelc.entity.dto.ExamDto;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;
import uz.pdp.cambridgelc.service.exam.ExamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {
    private final ExamService examService;

    @PostMapping("/add-exam")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<ExamEntity> addExam(
            @RequestBody ExamDto examDto
    ){
        return ResponseEntity.ok(examService.add(examDto));
    }

}
