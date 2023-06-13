package uz.pdp.cambridgelc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cambridgelc.entity.dto.LoginDto;
import uz.pdp.cambridgelc.entity.dto.response.JwtResponse;
import uz.pdp.cambridgelc.service.authUser.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    private ResponseEntity<JwtResponse>login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult bindingResult
            ){
        return ResponseEntity.ok(userService.login(loginDto,bindingResult));
    }
}
