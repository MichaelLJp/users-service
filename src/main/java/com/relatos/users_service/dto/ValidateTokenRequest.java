package com.relatos.users_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenRequest {

    @NotBlank(message = "El token es obligatorio")
    private String token;
}
