package com.giftcard.service;

import com.giftcard.model.GiftCard;
import com.giftcard.model.dto.CardResponseDTO;
import com.giftcard.repository.GiftCardRepository;
import com.giftcard.util.CardIdGenerator;
import com.giftcard.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class GiftCardService {

    @Value("${card.month.validity}")
    private long monthValidity;

    private final GiftCardRepository giftCardRepository;

    public GiftCard getCard(String documentId) throws ExecutionException, InterruptedException {
        return giftCardRepository.getCard(documentId);
    }

    public CardResponseDTO getCardInformation(String documentId) throws ExecutionException, InterruptedException {
        var card = giftCardRepository.getCard(documentId);
        return CardResponseDTO.builder()
                .cardId(card.getCardId())
                .message("Successfully retrieved gift card information!")
                .validUntil(card.getValidUntil())
                .build();
    }

    public CardResponseDTO createCard(GiftCard card) {
        try {
            var newCard = GiftCard.builder()
                    .cardId(generateCardId())
                    .company(card.getCompany())
                    .value(card.getValue())
                    .isValid(true)
                    .createdAt(DateUtils.getLocalDateTime(LocalDateTime.now()))
                    .validUntil(DateUtils.getLocalDateTime(LocalDateTime.now().plusMonths(monthValidity)))
                    .build();
            giftCardRepository.createCard(newCard);

            return CardResponseDTO.builder()
                    .cardId(newCard.getCardId())
                    .value(newCard.getValue())
                    .message("Successfully created a gift card!")
                    .validUntil(newCard.getValidUntil())
                    .build();
        } catch (Exception e) {
            return CardResponseDTO.builder().message("Error during gift card creation! Cause of error: " + e.getMessage()).build();
        }
    }

    public CardResponseDTO updateCard(GiftCard card) {
        giftCardRepository.updateCard(card);
        return CardResponseDTO.builder().message("Successfully updated a gift card!").build();
    }

    public CardResponseDTO deleteCard(String documentId) {
        giftCardRepository.deleteCard(documentId);
        return CardResponseDTO.builder().message("Successfully deleted a gift card!").build();
    }

    private String generateCardId() throws ExecutionException, InterruptedException {
        String cardId = CardIdGenerator.generateRandomCode();
        return giftCardRepository.cardExists(cardId) ? generateCardId() : cardId;
    }
}
