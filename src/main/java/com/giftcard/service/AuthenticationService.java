package com.giftcard.service;

import com.giftcard.dto.ResponseDTO;
import com.giftcard.model.AuthRequest;
import com.giftcard.model.AuthResponse;
import com.giftcard.model.RegisterRequest;
import com.giftcard.model.User;
import com.giftcard.repository.UserRepository;
import com.giftcard.util.DateUtils;
import com.giftcard.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static com.giftcard.constant.ApplicationConstants.USER_CREATED_CODE;
import static com.giftcard.constant.ApplicationConstants.USER_EXISTS_CODE;
import static com.giftcard.enums.RoleEnum.CLIENT;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseDTO register(RegisterRequest request) throws ExecutionException, InterruptedException {
        if (userRepository.userExists(request.getEmail())) {
            return ResponseDTO.builder()
                    .code(USER_EXISTS_CODE)
                    .message("User already exists. Try using a new email!")
                    .build();
        } else {
            var user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(CLIENT.getRole())
                    .company(request.getCompany())
                    .phoneNumber(request.getPhoneNumber())
                    .location(request.getPhoneNumber())
                    .createdAt(DateUtils.getLocalDateTime(LocalDateTime.now()))
                    .build();
            userRepository.createUser(user);
            return ResponseDTO.builder()
                    .code(USER_CREATED_CODE)
                    .message("Successfully registered a user!")
                    .build();
        }
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        try {
            var user = userRepository.getUser(request.getEmail());
            var jwt = jwtUtil.generateToken(user);
            return AuthResponse.builder()
                    .role(user.getRole())
                    .token(jwt)
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
