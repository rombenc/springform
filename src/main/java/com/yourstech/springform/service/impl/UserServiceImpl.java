package com.yourstech.springform.service.impl;

import com.yourstech.springform.dto.request.LoginRequest;
import com.yourstech.springform.dto.request.UserRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.LoginResponse;
import com.yourstech.springform.dto.response.UserResponse;
import com.yourstech.springform.exception.*;
import com.yourstech.springform.mapper.UserMapper;
import com.yourstech.springform.model.TokenBlacklist;
import com.yourstech.springform.model.User;
import com.yourstech.springform.repository.TokenBlacklistRepository;
import com.yourstech.springform.repository.UserRepository;
import com.yourstech.springform.security.JWTUtils;
import com.yourstech.springform.service.RoleService;
import com.yourstech.springform.service.UserService;
import com.yourstech.springform.service.ValidationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final EmailService emailService;
    private final JWTUtils jwtUtil;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public CommonResponse<Object> register(UserRequest request) {
        validationService.validate(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        var userRole = roleService.getRoleByName("USER");

        String token = generateSecureToken();

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isVerified(false)
                .verificationToken(token)
                .tokenExpiryDate(LocalDateTime.now().plusHours(24))
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        try {
            String verificationLink = baseUrl + "/verify?token=" + token;
            emailService.sendVerificationEmail(user.getEmail(), "Email Verification", verificationLink);
        } catch (Exception e) {
            throw new EmailSendException("Failed to send verification email to " + user.getEmail(), e);
        }

        return CommonResponse.builder()
                .status(201)
                .message("Successfully registered, please check your email to verify your account")
                .build();
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Transactional
    @Override
    public CommonResponse<Object> verifyAccount(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid verification token"));

        if (user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Verification token has expired");
        }

        if (user.isVerified()) {
            return CommonResponse.builder()
                    .status(200)
                    .message("Account already verified")
                    .build();
        }

        user.setVerifiedAt(LocalDateTime.now());
        user.setVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiryDate(null);
        userRepository.save(user);

        return CommonResponse.builder()
                .status(200)
                .message("Account successfully verified")
                .build();
    }

    @Transactional
    @Override
    public CommonResponse<LoginResponse> login(@Valid LoginRequest request) {
        validationService.validate(request);
        log.info("Attempting login for email: {}", request.getEmail());

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialException("Invalid email or password", 401));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialException("Invalid email or password", 401);
        }

        if (!user.isVerified()) {
            throw new UnverifiedUserException("Please continue your verification", 401);
        }

        String token = jwtUtil.generateToken(user);

        return CommonResponse.<LoginResponse>builder()
                .status(200)
                .message("Login successful")
                .data(LoginResponse.builder()
                        .id(user.getId())
                        .token(token)
                        .build())
                .build();
    }

    @Transactional
    @Override
    public CommonResponse<Object> logout(String token) {
        if (jwtUtil.isTokenExpired(token)) {
            throw new BadCredentialException("Unauthenticated.", 401);
        }

        if (tokenBlacklistRepository.existsByToken(token)) {
            throw new BadCredentialException("Unauthenticated.", 401);
        }

        TokenBlacklist blacklistedToken = TokenBlacklist.builder()
                .token(token)
                .expiryDate(jwtUtil.extractExpirationDate(token))
                .build();

        tokenBlacklistRepository.save(blacklistedToken);

        return CommonResponse.builder()
                .status(200)
                .message("Logout successful")
                .build();
    }

    @Override
    public CommonResponse<UserResponse> findById(Integer id) {

            var user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            UserResponse userResponse = userMapper.getUserResponse(user);
            return CommonResponse.<UserResponse>builder()
                    .status(200)
                    .message("User found")
                    .data(userResponse)
                    .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        log.info("User Email is: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));
    }

}

