package com.giftcard.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FirebaseCollectionsEnum {
    USERS("users"),
    CARDS("gift-cards");

    private final String name;
}
