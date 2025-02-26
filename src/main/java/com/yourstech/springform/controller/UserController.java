package com.yourstech.springform.controller;

import com.yourstech.springform.dto.request.LoginRequest;
import com.yourstech.springform.dto.request.UserRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.LoginResponse;
import com.yourstech.springform.exception.ResourceNotFoundException;
import com.yourstech.springform.exception.TokenExpiredException;
import com.yourstech.springform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TemplateEngine templateEngine;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<Object>> register(@Valid @RequestBody UserRequest request) {
        CommonResponse<Object> response =userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/verify")
    public String verifyAccount(@RequestParam String token, Model model) {
        var context = new Context();
        try {
            CommonResponse<Object> response = userService.verifyAccount(token);
            model.addAttribute("success", true);
            model.addAttribute("message", response.getMessage());
        } catch (ResourceNotFoundException | TokenExpiredException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Verification failed. Please try again or contact support.");
        }

        return templateEngine.process("verification-result", context);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        CommonResponse<LoginResponse> response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Object>> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String token = authHeader.substring(7); // Hapus "Bearer "
        return ResponseEntity.ok(userService.logout(token));
    }

}
