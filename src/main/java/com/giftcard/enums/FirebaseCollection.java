package com.giftcard.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FirebaseCollection {

    USERS("users"),
    CARDS("gift-cards");

    private final String name;
}
