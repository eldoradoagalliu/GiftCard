package com.giftcard.controller;

import com.giftcard.model.dto.ResponseDTO;
import com.giftcard.model.User;
import com.giftcard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.giftcard.constant.ApplicationConstants.API_VERSION_PATH;
import static com.giftcard.constant.ApplicationConstants.OLD_PASSWORD_MATCHES_CODE;
import static com.giftcard.constant.ApplicationConstants.PASSWORD_CHANGED_SUCCESSFULLY_CODE;

@RestController
@RequestMapping(API_VERSION_PATH + "/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.getUser(username));
        } catch (Exception e) {
            logger.error("Error getting user -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(user));
        } catch (Exception e) {
            logger.error("Error updating user -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteUser(@RequestParam String username) {
        try {
            return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting user -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseDTO> changePassword(Principal principal, @RequestParam String password) {
        try {
            ResponseDTO response = userService.changePassword(principal.getName(), password);
            if (response.getCode() == OLD_PASSWORD_MATCHES_CODE) {
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else if (response.getCode() == PASSWORD_CHANGED_SUCCESSFULLY_CODE) {
                return ResponseEntity.ok(response);
            } else {
                logger.error("Error during user password change -> {}", response.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error during user password change -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
