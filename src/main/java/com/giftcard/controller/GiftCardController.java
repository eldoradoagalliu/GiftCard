package com.giftcard.controller;

import com.giftcard.dto.ResponseDTO;
import com.giftcard.model.GiftCard;
import com.giftcard.service.GiftCardService;
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

@RestController
@RequestMapping("/api/v1/gift-cards")
@RequiredArgsConstructor
public class GiftCardController {

    private static final Logger logger = LoggerFactory.getLogger(GiftCardController.class);

    private final GiftCardService giftCardService;

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
    public ResponseEntity<ResponseDTO> createGiftCard(@RequestBody GiftCard card) {
        try {
            return new ResponseEntity<>(giftCardService.createCard(card), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateGiftCard(@RequestBody GiftCard card) {
        try {
            return ResponseEntity.ok(giftCardService.updateCard(card));
        } catch (Exception e) {
            logger.error("Error updating gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteGiftCard(@RequestParam String cardId) {
        try {
            return new ResponseEntity<>(giftCardService.deleteCard(cardId), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting gift card -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
