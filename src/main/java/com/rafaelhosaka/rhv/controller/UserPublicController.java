package com.rafaelhosaka.rhv.controller;

import com.rafaelhosaka.rhv.response.ErrorCode;
import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.response.UserResponse;
import com.rafaelhosaka.rhv.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/pb")
@RequiredArgsConstructor
public class UserPublicController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(userService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_ENTITY_NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_EXCEPTION));
        }
    }
}