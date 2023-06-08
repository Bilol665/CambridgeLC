package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.service.group.GroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/add/group")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT')")
    public ResponseEntity<GroupEntity> addGroup(
            @RequestBody GroupCreateDto groupCreateDto
    ){
            return ResponseEntity.ok(groupService.save(groupCreateDto));
    }

    @PostMapping("/add/student")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER','SUPPORT')")
    public ResponseEntity<GroupEntity> addStudent(
            @RequestParam UUID groupId,
            @RequestBody UserEntity student
    ){
        return ResponseEntity.ok(groupService.addStudent(groupId,student));
    }

    @GetMapping("/get/allGroups")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<GroupEntity>> getAllGroups(
            @RequestParam int page,
            @RequestParam int size
    ){
            return ResponseEntity.ok(groupService.getAllGroups(page,size));
    }

    @GetMapping("/get/group")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER','SUPPORT')")
    public ResponseEntity<GroupEntity> getGroup(
            @RequestParam UUID groupId
    ){
            return ResponseEntity.ok(groupService.getGroup(groupId));
    }

    @PostMapping("/update/name")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT','TEACHER')")
    public ResponseEntity<GroupEntity> updateName(
            @RequestParam UUID groupId,
            @RequestParam String name
    ){
            return ResponseEntity.ok(groupService.update(groupId,name));
    }

    @PostMapping("/update/teacher")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> updateTeacher(
            @RequestParam UUID groupId,
            @RequestBody UserEntity newTeacher
    ){
            return ResponseEntity.ok(groupService.update(groupId,newTeacher));
    }

    @PostMapping("/update/setFailedStudents")
    @PreAuthorize(value = "hasRole('TEACHER')")
    public ResponseEntity<GroupEntity> setFailedStudents(
            @RequestParam UUID groupId,
            @RequestBody List<UserEntity> failedStudents
    ){
        return ResponseEntity.ok(groupService.update(groupId,failedStudents));
    }
}
