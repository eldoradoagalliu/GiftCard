package com.giftcard.service;

import com.giftcard.model.dto.ResponseDTO;
import com.giftcard.model.authentication.AuthenticationRequest;
import com.giftcard.model.authentication.AuthenticationResponse;
import com.giftcard.model.RegisterRequest;
import com.giftcard.model.User;
import com.giftcard.repository.UserRepository;
import com.giftcard.util.DateUtils;
import com.giftcard.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static com.giftcard.constant.ApplicationConstants.*;
import static com.giftcard.enums.Role.CLIENT;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            User user = userRepository.getUser(email);
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException(INVALID_CREDENTIALS);
            }
            return new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());
        } catch (ExecutionException | InterruptedException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public ResponseDTO register(RegisterRequest request) throws ExecutionException, InterruptedException {
        if (userRepository.userExists(request.getEmail())) {
            return ResponseDTO.builder()
                    .code(USER_EXISTS_CODE)
                    .message(USER_ALREADY_EXISTS)
                    .build();
        } else {
            var user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(CLIENT.getRole())
                    .company(request.getCompany())
                    .phoneNumber(request.getPhoneNumber())
                    .location(request.getLocation())
                    .createdAt(DateUtils.getLocalDateTime(LocalDateTime.now()))
                    .build();
            userRepository.createUser(user);

            return ResponseDTO.builder()
                    .code(USER_CREATED_CODE)
                    .message(SUCCESSFUL_REGISTRATION)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authenticationResult = authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authenticationResult.isAuthenticated()) {
            try {
                var user = userRepository.getUser(request.getEmail());
                var jwt = jwtUtil.generateToken(user);

                return AuthenticationResponse.builder()
                        .role(user.getRole())
                        .token(jwt)
                        .message(SUCCESSFUL_AUTHENTICATION)
                        .build();
            } catch (Exception e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        } else {
            throw new BadCredentialsException(INVALID_CREDENTIALS);
        }
    }
}
