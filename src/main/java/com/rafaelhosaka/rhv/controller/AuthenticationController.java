package com.rafaelhosaka.rhv.controller;

import com.rafaelhosaka.rhv.request.AuthenticationRequest;
import com.rafaelhosaka.rhv.request.RegisterRequest;
import com.rafaelhosaka.rhv.response.ErrorCode;
import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request){
        try{
            return ResponseEntity.ok(authenticationService.register(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request){
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.AS_BAD_CREDENTIALS));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.AS_EXCEPTION));
        }
    }
}
