package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.UserCreateDto;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.entity.user.UserRole;
import uz.pdp.cambridgelc.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
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

    @PostMapping("/addSuperAdmin")
    private ResponseEntity<UserEntity> addSuperAdmin(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto,List.of(UserRole.ROLE_SUPER_ADMIN)));
    }

    @PostMapping("/addSupport")
    private ResponseEntity<UserEntity> addSupport(
            @RequestBody UserCreateDto dto
    ){
        return ResponseEntity.ok(userService.saveUser(dto, List.of(UserRole.ROLE_SUPPORT)));
    }


    @PutMapping("/updateCredits/{id}")
    private ResponseEntity<String>updateUserCreditsById(
            @RequestParam Integer credits,
            @PathVariable UUID id
    ){
        userService.updateCreditsById(credits,id);
        return ResponseEntity.status(200).body("Successfully updated credits");
    }

}
