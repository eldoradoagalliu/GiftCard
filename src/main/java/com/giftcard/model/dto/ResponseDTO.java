package com.giftcard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private int code;
    private String message;
}
