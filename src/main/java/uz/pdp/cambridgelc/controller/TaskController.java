package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.TaskDto;
import uz.pdp.cambridgelc.entity.exam.TaskEntity;
import uz.pdp.cambridgelc.service.exam.TaskService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/add/{examId}")
    @PreAuthorize(value = "hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<TaskEntity> add(
            @Valid @RequestBody TaskDto taskDto,
            BindingResult bindingResult,
            @PathVariable UUID examId
    ) {
        return ResponseEntity.ok(taskService.save(taskDto,examId,bindingResult));
    }
    @PutMapping("/check/{taskId}")
    @PreAuthorize(value = "hasRole('STUDENT')")
    public ResponseEntity<Boolean> checkTask(
            @PathVariable UUID taskId,
            @RequestParam(required = false,defaultValue = "") String answer,
            @RequestParam UUID examId,
            Principal principal
    ){
        return new ResponseEntity<>(taskService.check(taskId,answer,principal,examId), HttpStatus.OK);
    }
    @PutMapping("/assign/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<TaskEntity> assign(
            @PathVariable UUID taskId,
            @RequestParam UUID examId
    ){
        return new ResponseEntity<>(taskService.assign(taskId, examId),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable UUID taskId
    ) {
        taskService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
