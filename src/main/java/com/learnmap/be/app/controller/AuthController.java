package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqLoginDto;
import com.learnmap.be.domain.dto.ReqRegisterDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ReqLoginDto loginDto) {
        return authService.login(loginDto);
    }
    @PostMapping("/register")
    public Account register(@Valid @RequestBody ReqRegisterDto reqRegisterDTO) {
        return authService.register(reqRegisterDTO);
    }
//    @Operation(summary = "Refresh token")
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
//        String newAccessToken = authService.refreshToken(refreshToken);
//        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
//    }

//    @Operation(summary = "User logout")
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        authService.logout(request);
//        return ResponseEntity.ok("Logged out successfully");
//    }
}
