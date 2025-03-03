package com.giftcard.controller;

import com.giftcard.dto.ResponseDTO;
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

@RestController
@RequestMapping("/api/v1/users")
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

    //TODO: Who will handle the change, client or/and admin ?
    @PostMapping
    public ResponseEntity<Byte> changePassword(Principal principal, @RequestParam String password) {
        try {
            return ResponseEntity.ok(userService.changePassword(principal.getName(), password));
        } catch (Exception e) {
            logger.error("Error during user password change -> {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
