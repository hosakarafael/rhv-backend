package com.rafaelhosaka.gateway.client;

import com.rafaelhosaka.gateway.dto.UserRequest;
import com.rafaelhosaka.gateway.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user", url = "http://localhost:8082")
public interface UserClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/user")
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest);

    @RequestMapping(method = RequestMethod.GET,value = "/api/user/email/{email}")
    ResponseEntity<UserResponse> findByEmail(@PathVariable("email") String id);
}
