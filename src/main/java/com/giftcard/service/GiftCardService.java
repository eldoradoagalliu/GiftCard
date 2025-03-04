package com.giftcard.service;

import com.giftcard.model.dto.ResponseDTO;
import com.giftcard.model.GiftCard;
import com.giftcard.repository.GiftCardRepository;
import com.giftcard.util.CardIdGenerator;
import com.giftcard.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class GiftCardService {

    private final GiftCardRepository giftCardRepository;
    private final EmailService emailService;

    public GiftCard getCard(String documentId) throws ExecutionException, InterruptedException {
        return giftCardRepository.getCard(documentId);
    }

    public ResponseDTO createCard(GiftCard card) {
        try {
            var newCard = GiftCard.builder()
                    .cardId(generateCardId())
                    .company(card.getCompany())
                    .value(card.getValue())
                    .isValid(Boolean.TRUE)
                    .createdAt(DateUtils.getLocalDateTime(LocalDateTime.now()))
                    //TODO: Check the validation time
                    .validUntil(DateUtils.getLocalDateTime(LocalDateTime.now().plusMonths(6)))
                    .build();
            giftCardRepository.createCard(newCard);
            emailService.sendEmail("eldoradoagalliu1@gmail.com", "Gift card created successfully!",
                    "Hello user, \nYour gift card with card id: "+ newCard.getCardId() +
                            " has been created successfully!\nThank you for choosing our coupon service!"
            );
            return ResponseDTO.builder().message("Successfully created a gift card!").build();
        } catch (Exception e) {
            return ResponseDTO.builder().message("Error during gift card creation! Cause of error: " + e.getMessage()).build();
        }
    }

    /**
     * General update of all the user fields
     */
    public ResponseDTO updateCard(GiftCard card) {
        giftCardRepository.updateCard(card);
        return ResponseDTO.builder().message("Successfully updated a gift card!").build();
    }

    public ResponseDTO deleteCard(String documentId) {
        giftCardRepository.deleteCard(documentId);
        return ResponseDTO.builder().message("Successfully deleted a gift card!").build();
    }

    private String generateCardId() throws ExecutionException, InterruptedException {
        String cardId = CardIdGenerator.generateRandomCode();
        if (giftCardRepository.cardExists(cardId)) {
            return generateCardId();
        }
        return cardId;
    }
}
