package com.rafaelhosaka.rhv.controller;

import com.rafaelhosaka.rhv.request.HistoryRequest;
import com.rafaelhosaka.rhv.request.SubscribeRequest;
import com.rafaelhosaka.rhv.request.UserRequest;
import com.rafaelhosaka.rhv.response.ErrorCode;
import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.response.UserResponse;
import com.rafaelhosaka.rhv.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(userService.createUser(userRequest));
    }

    @GetMapping("/email/{email}")
    ResponseEntity<Response> findByEmail(@PathVariable("email") String email){
        try{
            return ResponseEntity.ok().body(userService.findByEmail(email));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_ENTITY_NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_EXCEPTION));
        }
    }

    @GetMapping("/history/{user_id}")
    public ResponseEntity findHistoryByUserId(@PathVariable("user_id") Integer userId){
        try {
            return ResponseEntity.ok().body(userService.findHistoryByUserId(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_EXCEPTION));
        }
    }

    @PostMapping("/history")
    public void registerHistory(@RequestBody HistoryRequest historyRequest){
        userService.registerHistory(historyRequest);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Response> subscribe(@RequestBody SubscribeRequest subscribeRequest){
        try{
            return ResponseEntity.ok().body(userService.subscribeToUser(subscribeRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Response>  unsubscribe(@RequestBody SubscribeRequest subscribeRequest){
        try{
            return ResponseEntity.ok().body(userService.unsubscribeFromUser(subscribeRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam("profileImageFile") MultipartFile profileImageFile, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        try {
            return ResponseEntity.ok().body(userService.uploadProfileImage(profileImageFile, authHeader));
        }catch (IOException e){
            return ResponseEntity.internalServerError().body(new Response(e.getMessage(), ErrorCode.US_UPLOAD_FAILED));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.US_EXCEPTION));
        }
    }
}
