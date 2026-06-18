package com.relatos.users_service.controller;

import com.relatos.users_service.dto.LoginRequest;
import com.relatos.users_service.dto.RefreshTokenRequest;
import com.relatos.users_service.dto.TokenResponse;
import com.relatos.users_service.dto.ValidateTokenRequest;
import com.relatos.users_service.dto.ValidateTokenResponse;
import com.relatos.users_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> createToken(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.createToken(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/token/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@Valid @RequestBody ValidateTokenRequest request) {
        ValidateTokenResponse response = authService.validateToken(request.getToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refreshToken(request.getToken());
        return ResponseEntity.ok(response);
    }
}
