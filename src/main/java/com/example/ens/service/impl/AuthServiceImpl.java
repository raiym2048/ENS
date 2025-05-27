package com.example.ens.service.impl;

import com.example.ens.dto.auth.AuthRequest;
import com.example.ens.dto.auth.AuthResponse;
import com.example.ens.exception.BadRequestException;
import com.example.ens.exception.NotFoundException;
import com.example.ens.model.User;
import com.example.ens.repository.UserRepository;
import com.example.ens.security.JwtUtil;
import com.example.ens.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found", HttpStatus.NOT_FOUND));
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public String register(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }
}
