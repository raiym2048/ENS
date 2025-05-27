package com.example.ens.service;

import com.example.ens.dto.auth.AuthRequest;
import com.example.ens.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    String register(AuthRequest request);
}
