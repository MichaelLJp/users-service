package com.relatos.users_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {

    private boolean valid;

    private String accessToken;

    private Long userId;

    private String email;

    private String role;
}
