package com.giftcard.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");

    private final String role;
}
