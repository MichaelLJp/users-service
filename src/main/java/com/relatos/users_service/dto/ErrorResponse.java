package com.relatos.users_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
