package com.giftcard.repository.implementation;

import com.giftcard.model.GiftCard;
import com.giftcard.repository.GiftCardRepository;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

import static com.giftcard.enums.FirebaseCollectionsEnum.CARDS;

@Repository
public class GiftCardRepositoryImpl implements GiftCardRepository {

    @Override
    public GiftCard getCard(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(CARDS.getName()).document(documentId);
        DocumentSnapshot document = documentReference.get().get();
        return document.exists() ? document.toObject(GiftCard.class) : null;
    }

    @Override
    public void createCard(GiftCard card) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(CARDS.getName()).document(card.getCardId()).set(card);
    }

    @Override
    public void updateCard(GiftCard card) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(CARDS.getName()).document(card.getCardId()).set(card);
    }

    @Override
    public void deleteCard(String documentId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(CARDS.getName()).document(documentId).delete();
    }

    @Override
    public boolean cardExists(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(CARDS.getName()).document(documentId);
        DocumentSnapshot document = documentReference.get().get();
        return document.exists();
    }
}
