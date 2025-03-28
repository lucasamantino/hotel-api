package com.hotel.hotelease.controller;

import com.hotel.hotelease.dto.AuthRequest;
import com.hotel.hotelease.dto.AuthResponse;
import com.hotel.hotelease.entity.Role;
import com.hotel.hotelease.entity.User;
import com.hotel.hotelease.repository.RoleRepository;
import com.hotel.hotelease.repository.UserRepository;
import com.hotel.hotelease.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        if (authResponse.accessToken().equals("")) {
            throw new BadCredentialsException("Email or password is invalid!");
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.register(authRequest);
        if (authResponse.accessToken().equals("")) {
            throw new BadCredentialsException("Email already exists!");
        }
        return ResponseEntity.ok(authResponse);
    }

}
