package com.giftcard.service;

import com.giftcard.dto.ResponseDTO;
import com.giftcard.dto.UserInfoDTO;
import com.giftcard.model.User;
import com.giftcard.repository.UserRepository;
import com.giftcard.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(String documentId) throws ExecutionException, InterruptedException {
        return userRepository.getUser(documentId);
    }

    public UserInfoDTO getUserInfo(String documentId) throws ExecutionException, InterruptedException {
        var user = getUser(documentId);
        return UserInfoDTO.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    /**
     * General update of all the user fields
     */
    public ResponseDTO updateUser(User user) {
        var editedUser = User.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .company(user.getCompany())
                .branchName(user.getBranchName())
                .phoneNumber(user.getPhoneNumber())
                .location(user.getLocation())
                .createdAt(user.getCreatedAt())
                .updatedAt(DateUtils.getLocalDateTime(LocalDateTime.now()))
                .build();
        userRepository.updateUser(editedUser);
        return ResponseDTO.builder().message("Successfully updated a user!").build();
    }

    public ResponseDTO deleteUser(String documentId) {
        userRepository.deleteUser(documentId);
        return ResponseDTO.builder().message("Successfully deleted a user!").build();
    }

    public Byte changePassword(String userEmail, String password) throws ExecutionException, InterruptedException {
        String currentUserPassword = userRepository.getUserPassword(userEmail);
        String encodedNewPassword = passwordEncoder.encode(password);

        if (passwordEncoder.matches(password, currentUserPassword)) {
            return 0;
        } else {
            userRepository.updatePassword(userEmail, encodedNewPassword);
            return 1;
        }
    }
}
