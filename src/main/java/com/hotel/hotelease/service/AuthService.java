package com.hotel.hotelease.service;

import com.hotel.hotelease.dto.AuthRequest;
import com.hotel.hotelease.dto.AuthResponse;
import com.hotel.hotelease.entity.Role;
import com.hotel.hotelease.entity.User;
import com.hotel.hotelease.repository.RoleRepository;
import com.hotel.hotelease.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final JwtEncoder jwtEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(JwtEncoder jwtEncoder,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.email());
        if (user == null || !user.isCredentialsCorrect(authRequest, passwordEncoder)) {
            return new AuthResponse("",0L);
        }
        Instant now = Instant.now();
        long expires = 300L;

        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .expiresAt(now.plusSeconds(expires))
                .claim("scope", scope)

                .issuedAt(now).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponse(jwtValue, expires);
    }

    public AuthResponse register(AuthRequest authRequest) {
        User euser = userRepository.findByEmail(authRequest.email());
        if (euser != null) {
            return new AuthResponse("",0L);
        }
        Instant now = Instant.now();
        long expires = 300L;

        User user = new User();
        Role role = roleRepository.findByName("BASIC");

        user.setName(authRequest.name());
        user.setEmail(authRequest.email());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .expiresAt(now.plusSeconds(expires))
                .claim("scope", scope)
                .issuedAt(now).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponse(jwtValue, expires);
    }
}
