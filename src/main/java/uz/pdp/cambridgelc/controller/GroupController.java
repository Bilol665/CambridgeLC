package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.service.group.GroupService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;


    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    public ResponseEntity<Object> deleteGroup(
            @RequestParam String name
    ){
        groupService.deleteGroupByName(name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> addGroup(
            @Valid @RequestBody GroupCreateDto groupCreateDto,
            BindingResult bindingResult
    ){
            return ResponseEntity.ok(groupService.save(bindingResult,groupCreateDto));
    }

    @GetMapping("/get")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER','SUPPORT')")
    public ResponseEntity<GroupEntity> getGroup(
            @RequestParam UUID groupId
    ){
            return ResponseEntity.ok(groupService.getGroup(groupId));
    }

    @PostMapping("/updateName")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT','TEACHER')")
    public ResponseEntity<GroupEntity> updateName(
            @RequestParam UUID groupId,
            @RequestParam String name
    ){
            return ResponseEntity.ok(groupService.updateGroupName(groupId,name));
    }

    @PostMapping("/updateTeacher")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> updateTeacher(
            @RequestParam UUID groupId,
            @RequestParam String newTeacher
    ){
            return ResponseEntity.ok(groupService.updateTeacher(groupId,newTeacher));
    }

    @GetMapping("/getAllGroups")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<GroupEntity>> getAllGroups(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10") int size,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(groupService.getAllGroups(bindingResult,page,size));
    }
    @GetMapping("/getGroupsByTeacher")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<GroupEntity>> getAllByTeacher(
            @RequestParam  String username
    ){
        return ResponseEntity.ok(groupService.getGroupsByTeacher(username));
    }
}
