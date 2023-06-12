package uz.pdp.cambridgelc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.LoginDto;
import uz.pdp.cambridgelc.entity.dto.response.JwtResponse;
import uz.pdp.cambridgelc.service.auth_user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    private ResponseEntity<JwtResponse>login(
            @RequestBody LoginDto loginDto
            ){
        return ResponseEntity.ok(userService.login(loginDto));
    }
}
