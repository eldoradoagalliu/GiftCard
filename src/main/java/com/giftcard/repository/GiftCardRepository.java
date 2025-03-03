package com.giftcard.repository;

import com.giftcard.model.GiftCard;

import java.util.concurrent.ExecutionException;

public interface GiftCardRepository {

    GiftCard getCard(String documentId) throws ExecutionException, InterruptedException;

    void createCard(GiftCard card);

    void updateCard(GiftCard card);

    void deleteCard(String documentId);

    boolean cardExists(String documentId) throws ExecutionException, InterruptedException;
}
