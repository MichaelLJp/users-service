package com.relatos.users_service.service;

import com.relatos.users_service.dto.RegisterRequest;
import com.relatos.users_service.dto.UserResponse;
import com.relatos.users_service.exception.UserBusinessException;
import com.relatos.users_service.model.Role;
import com.relatos.users_service.model.User;
import com.relatos.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserBusinessException("Ya existe un usuario con ese email", HttpStatus.CONFLICT);
        }

        Role role = Role.USER;
        if (request.getRole() != null && !request.getRole().isBlank()) {
            try {
                role = Role.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new UserBusinessException("Rol no valido: " + request.getRole());
            }
        }

        User user = User.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        return toResponse(userRepository.save(user));
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(
                        "Usuario no encontrado con ID: " + id, HttpStatus.NOT_FOUND));
        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
