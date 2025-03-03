package com.giftcard.controller;

import com.giftcard.dto.ResponseDTO;
import com.giftcard.model.AuthRequest;
import com.giftcard.model.AuthResponse;
import com.giftcard.model.RegisterRequest;
import com.giftcard.service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequest request) {
        try {
            ResponseDTO response = authenticationService.register(request);
            if (response.getCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else if (response.getCode() == 2) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error registering user -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (Exception e) {
            logger.error("Error authenticating user -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a custom jwt auth token
     *
     * @param username the user's email
     * @return a custom token
     */
    @PreAuthorize("hasRole='ADMIN'")
    @GetMapping("/custom/token")
    public ResponseEntity<String> createCustomToken(@RequestParam String username) {
        try {
            return ResponseEntity.ok(FirebaseAuth.getInstance().createCustomToken(username));
        } catch (FirebaseAuthException e) {
            logger.error("Error creating custom token -> {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
