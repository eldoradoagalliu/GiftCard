package com.giftcard.controller;

import com.giftcard.model.GiftCard;
import com.giftcard.model.dto.CardResponseDTO;
import com.giftcard.model.dto.UserInfoDTO;
import com.giftcard.service.EmailService;
import com.giftcard.service.GiftCardService;
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

import static com.giftcard.constant.ApplicationConstants.API_VERSION_PATH;

@RestController
@RequestMapping(API_VERSION_PATH + "/gift-card")
@RequiredArgsConstructor
public class GiftCardController {

    private static final Logger logger = LoggerFactory.getLogger(GiftCardController.class);

    private final UserService userService;
    private final GiftCardService giftCardService;
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<GiftCard> getGiftCard(@RequestParam String cardId) {
        try {
            return ResponseEntity.ok(giftCardService.getCard(cardId));
        } catch (Exception e) {
            logger.error("Error getting gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> createGiftCard(@RequestBody GiftCard card) {
        try {
            UserInfoDTO userInfo = userService.retrieveLoggedInUserInfo();
            CardResponseDTO response = giftCardService.createCard(card);
            emailService.sendGiftCardEmail(userInfo, response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error creating gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<CardResponseDTO> updateGiftCard(@RequestBody GiftCard card) {
        try {
            return ResponseEntity.ok(giftCardService.updateCard(card));
        } catch (Exception e) {
            logger.error("Error updating gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<CardResponseDTO> deleteGiftCard(@RequestParam String cardId) {
        try {
            return new ResponseEntity<>(giftCardService.deleteCard(cardId), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<CardResponseDTO> validateGiftCard(@RequestParam String cardId) {
        try {
            UserInfoDTO userInfo = userService.retrieveLoggedInUserInfo();
            CardResponseDTO response = giftCardService.getCardInformation(cardId);
            emailService.sendCardValidationEmail(userInfo, response);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (Exception e) {
            logger.error("Error validating gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
