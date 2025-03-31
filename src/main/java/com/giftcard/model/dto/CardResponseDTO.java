package com.giftcard.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CardResponseDTO {
    private String cardId;
    private Double value;
    private String message;
    private Date validUntil;
}
