package com.rafaelhosaka.gateway.service;

import com.rafaelhosaka.gateway.client.UserClient;
import com.rafaelhosaka.gateway.dto.*;
import com.rafaelhosaka.gateway.models.Role;
import com.rafaelhosaka.gateway.models.User;
import com.rafaelhosaka.gateway.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;

    public RegisterResponse register(RegisterRequest request) throws Exception {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new Exception("User with email "+request.getEmail()+" already exists");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var userResponse = userClient.createUser(new UserRequest(
                null,
                request.getName(),
                user.getEmail(),
                null,
                new Date()
        ));

        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
                .user(userResponse.getBody())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            throw new Exception("Email cannot be empty");
        }

        if(request.getPassword() == null || request.getPassword().isEmpty()){
            throw new Exception("Password cannot be empty");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Video with Email "+request.getEmail()+" not found"));
        var userResponse = userClient.findByEmail(user.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(userResponse.getBody())
                .token(jwtToken)
                .build();
    }
}
