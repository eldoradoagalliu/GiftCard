package com.giftcard.util;

import java.util.Random;

import static com.giftcard.constant.ApplicationConstants.CARD_ID_LENGTH;
import static com.giftcard.constant.ApplicationConstants.ID_CHARACTERS;

public class CardIdGenerator {

    private static final Random random = new Random();

    public static String generateRandomCode() {
        StringBuilder code = new StringBuilder(CARD_ID_LENGTH);
        for (int i = 0; i < CARD_ID_LENGTH; i++) {
            int index = random.nextInt(ID_CHARACTERS.length());
            code.append(ID_CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
