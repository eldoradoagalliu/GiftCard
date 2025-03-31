package com.giftcard.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDTO {
    private String fullName;
    private String email;
    private String role;
}
