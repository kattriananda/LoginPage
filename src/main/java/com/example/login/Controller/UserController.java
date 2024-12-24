package com.example.login.Controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.Response.AuthenticationData;
import com.example.login.Response.AuthenticationResponse;
import com.example.login.Response.LoginRequest;
import com.example.login.Response.RegisterRequest;
import com.example.login.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/regis")
    public ResponseEntity<AuthenticationData<AuthenticationResponse>> register(
            @Valid @RequestBody RegisterRequest request, Errors errors) {
        AuthenticationData<AuthenticationResponse> responseData = new AuthenticationData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus("failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus("OK");
        responseData.setMessages(Collections.singletonList("Success"));
        responseData.setPayload(userService.register(request));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationData<AuthenticationResponse>> login(@Valid @RequestBody LoginRequest request,
            Errors errors) {
        AuthenticationData<AuthenticationResponse> responseData = new AuthenticationData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus("OK");
        responseData.setMessages(Collections.singletonList("SUCCESS"));
        responseData.setPayload(userService.authenticate(request));
        return ResponseEntity.ok(responseData);

    }

}
