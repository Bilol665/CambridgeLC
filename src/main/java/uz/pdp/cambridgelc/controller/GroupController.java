package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.service.group.GroupService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;


    @DeleteMapping("/delete/group")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Object> deleteGroup(
            @RequestParam String name
    ){
      groupService.deleteGroupByName(name);
      return ResponseEntity.status(200).body("Group successfully deleted");
    }

    @PostMapping("/add/group")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> addGroup(
            @RequestBody GroupCreateDto groupCreateDto
    ){
            return ResponseEntity.ok(groupService.save(groupCreateDto));
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
            return ResponseEntity.ok(groupService.updateGroupName(groupId,name));
    }

    @PostMapping("/update/teacher")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> updateTeacher(
            @RequestParam UUID groupId,
            @RequestParam String newTeacher
    ){
            return ResponseEntity.ok(groupService.updateTeacher(groupId,newTeacher));
    }
    @PostMapping("/add/student")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> addStudent(
            @RequestParam UUID courseId,
            @RequestParam String studentUsername
    ){
        return ResponseEntity.ok(groupService.addStudent(courseId,studentUsername));
    }

    @GetMapping("/get/allGroups")
    public ResponseEntity<List<GroupEntity>> getAllGroups(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(groupService.getAllGroups(page,size));
    }
    @GetMapping("/get/allGroupsByTeacher")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<GroupEntity>> getAllByTeacher(
            @RequestParam String username
    ){
        return ResponseEntity.ok(groupService.getGroupsByTeacher(username));
    }
}
