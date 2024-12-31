package com.rafaelhosaka.gateway.service;

import com.rafaelhosaka.gateway.client.UserClient;
import com.rafaelhosaka.gateway.dto.*;
import com.rafaelhosaka.gateway.models.Role;
import com.rafaelhosaka.gateway.models.AuthUser;
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

    public Response register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return new Response("User with email "+request.getEmail()+" already exists", ErrorCode.AS_DUPLICATE_EMAIL);
        }
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            return new Response("Email cannot be empty", ErrorCode.AS_EMAIL_EMPTY);
        }

        if(request.getPassword() == null || request.getPassword().isEmpty()){
            return new Response("Password cannot be empty", ErrorCode.AS_PASSWORD_EMPTY);
        }
        if(request.getName() == null || request.getName().isEmpty()){
            return new Response("Name cannot be empty", ErrorCode.AS_NAME_EMPTY);
        }

        var user = AuthUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var userResponse = userClient.createUser(new UserRequest(
                null,
                request.getName(),
                user.getEmail(),
                new Date()
        ));

        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
                .user(userResponse.getBody())
                .token(jwtToken)
                .build();
    }

    public Response authenticate(AuthenticationRequest request) throws Exception {
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            return new Response("Email cannot be empty", ErrorCode.AS_EMAIL_EMPTY);
        }

        if(request.getPassword() == null || request.getPassword().isEmpty()){
            return new Response("Password cannot be empty", ErrorCode.AS_PASSWORD_EMPTY);
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
