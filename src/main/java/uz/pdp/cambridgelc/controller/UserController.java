package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.entity.user.UserRole;
import uz.pdp.cambridgelc.service.authUser.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/addStudent")
    private ResponseEntity<UserEntity> addStudent(
            @Valid @RequestBody  UserCreateDto dto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_STUDENT),bindingResult));
    }


    @PostMapping("/addTeacher")
    private ResponseEntity<UserEntity> addTeacher(
            @Valid @RequestBody  UserCreateDto dto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_TEACHER),bindingResult));
    }


    @PostMapping("/addAdmin")
    private ResponseEntity<UserEntity> addAdmin(
            @Valid @RequestBody  UserCreateDto dto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_ADMIN),bindingResult));
    }

    @PostMapping("/addSuperAdmin")
    private ResponseEntity<UserEntity> addSuperAdmin(
            @Valid @RequestBody  UserCreateDto dto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(userService.saveUser(dto,List.of(UserRole.ROLE_SUPER_ADMIN),bindingResult));
    }

    @PostMapping("/addSupport")
    private ResponseEntity<UserEntity> addSupport(
            @Valid @RequestBody  UserCreateDto dto,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_SUPPORT),bindingResult));
    }

    @PostMapping("/add/groupStudent")
    public ResponseEntity<GroupEntity> addStudent(
            @RequestParam  UUID courseId,
            @RequestParam(required = false,defaultValue = "")  String studentUsername
    ){
        return ResponseEntity.ok(userService.addStudent(courseId,studentUsername));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String>updateCreditsById(
             @Valid @PathVariable  UUID id,
             @Valid @RequestParam  Integer credits,
            BindingResult bindingResult
    ){
        userService.updateCreditsById(id,credits,bindingResult);
        return ResponseEntity.status(200).body("Successfully updated");
    }
}
