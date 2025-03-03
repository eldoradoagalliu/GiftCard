package com.giftcard.repository;

import com.giftcard.model.User;

import java.util.concurrent.ExecutionException;

public interface UserRepository {

    User getUser(String documentId) throws ExecutionException, InterruptedException;

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(String documentId);

    boolean userExists(String documentId) throws ExecutionException, InterruptedException;

    String getUserPassword(String documentId) throws ExecutionException, InterruptedException;

    void updatePassword(String documentId, String newPassword);
}
