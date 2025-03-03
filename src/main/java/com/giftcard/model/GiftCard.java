package com.giftcard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard {
    private String cardId;
    private String company;
    private Double value;
    private boolean isValid;
    private Date createdAt;
    private Date validUntil;
}
