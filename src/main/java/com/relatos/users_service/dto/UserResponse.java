package com.relatos.users_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String nombre;

    private String email;

    private String role;

    private LocalDateTime createdAt;
}
