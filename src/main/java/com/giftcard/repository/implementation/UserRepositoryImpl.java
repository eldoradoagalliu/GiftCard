package com.giftcard.repository.implementation;

import com.giftcard.model.User;
import com.giftcard.repository.UserRepository;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.giftcard.enums.FirebaseCollectionsEnum.USERS;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User getUser(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(USERS.getName()).document(documentId);
        DocumentSnapshot document = documentReference.get().get();
        return document.exists() ? document.toObject(User.class) : null;
    }

    @Override
    public void createUser(User user) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(USERS.getName()).document(user.getUsername()).set(user);
    }

    @Override
    public void updateUser(User user) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(USERS.getName()).document(user.getUsername()).set(user);
    }

    @Override
    public void deleteUser(String documentId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(USERS.getName()).document(documentId).delete();
    }

    @Override
    public boolean userExists(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(USERS.getName()).document(documentId);
        DocumentSnapshot document = documentReference.get().get();
        return document.exists();
    }

    @Override
    public String getUserPassword(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(USERS.getName()).document(documentId);
        DocumentSnapshot document = documentReference.get().get();
        if (document.exists()) {
            return Objects.requireNonNull(document.get("password")).toString();
        } else {
            return "The user does not exist";
        }
    }

    @Override
    public void updatePassword(String documentId, String newPassword) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(USERS.getName()).document(documentId).update("password", newPassword);
    }
}
