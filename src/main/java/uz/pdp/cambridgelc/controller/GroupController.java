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
            @Valid @RequestParam String name,
            BindingResult bindingResult
    ){
      groupService.deleteGroupByName(bindingResult,name);
      return ResponseEntity.status(204).build();
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> addGroup(
            @RequestBody @Valid GroupCreateDto groupCreateDto,
            BindingResult bindingResult
    ){
            return ResponseEntity.ok(groupService.save(bindingResult,groupCreateDto));
    }

    @GetMapping("/get")
    @PreAuthorize(value = "hasAnyRole('ADMIN','TEACHER','SUPPORT')")
    public ResponseEntity<GroupEntity> getGroup(
            @RequestParam @Valid UUID groupId,
            BindingResult bindingResult
    ){
            return ResponseEntity.ok(groupService.getGroup(bindingResult,groupId));
    }

    @PostMapping("/updateName")
    @PreAuthorize(value = "hasAnyRole('ADMIN','SUPPORT','TEACHER')")
    public ResponseEntity<GroupEntity> updateName(
            @RequestParam @Valid UUID groupId,
            @RequestParam @Valid String name,
            BindingResult bindingResult
    ){
            return ResponseEntity.ok(groupService.updateGroupName(bindingResult,groupId,name));
    }

    @PostMapping("/updateTeacher")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<GroupEntity> updateTeacher(
            @RequestParam @Valid UUID groupId,
            @RequestParam @Valid String newTeacher,
            BindingResult bindingResult
    ){
            return ResponseEntity.ok(groupService.updateTeacher(bindingResult,groupId,newTeacher));
    }

    @GetMapping("/getAllGroups")
    public ResponseEntity<List<GroupEntity>> getAllGroups(
            @RequestParam @Valid int page,
            @RequestParam @Valid int size,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(groupService.getAllGroups(bindingResult,page,size));
    }
    @GetMapping("/getAllGroupsByTeacher")
    @PreAuthorize(value = "permitAll()")
    public ResponseEntity<List<GroupEntity>> getAllByTeacher(
            @RequestParam @Valid String username,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(groupService.getGroupsByTeacher(bindingResult,username));
    }
}
