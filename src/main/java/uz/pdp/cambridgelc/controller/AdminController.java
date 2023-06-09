package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.entity.user.UserRole;
import uz.pdp.cambridgelc.service.UserService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    @PostMapping("/addStudent")
    private ResponseEntity<UserEntity> addStudent(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_STUDENT)));
    }


    @PostMapping("/addTeacher")
    private ResponseEntity<UserEntity> addTeacher(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_TEACHER)));
    }


    @PostMapping("/addAdmin")
    private ResponseEntity<UserEntity> addAdmin(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_ADMIN)));
    }

    @PostMapping("/addSupport")
    private ResponseEntity<UserEntity> addSupport(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_SUPPORT)));
    }
}
