package com.relatos.users_service.service;

import com.relatos.users_service.dto.LoginRequest;
import com.relatos.users_service.dto.TokenResponse;
import com.relatos.users_service.dto.ValidateTokenResponse;
import com.relatos.users_service.exception.UserBusinessException;
import com.relatos.users_service.model.User;
import com.relatos.users_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenStoreService tokenStore;

    /**
     * Crea token: valida credenciales, genera JWT + token opaco y guarda la
     * relacion en Redis. Devuelve unicamente el token opaco al cliente.
     */
    public TokenResponse createToken(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserBusinessException(
                        "Credenciales invalidas", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserBusinessException("Credenciales invalidas", HttpStatus.UNAUTHORIZED);
        }

        return issueTokens(user);
    }

    /**
     * Valida token: recibe el token opaco, busca el JWT asociado en Redis y
     * comprueba que no este expirado. Devuelve el JWT y los claims para que el
     * Gateway lo inyecte como header accessToken.
     */
    public ValidateTokenResponse validateToken(String opaqueToken) {
        String jwt = tokenStore.getJwt(opaqueToken);

        if (jwt == null || !jwtService.isTokenValid(jwt)) {
            throw new UserBusinessException("Token invalido o expirado", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = jwtService.parseClaims(jwt);

        return ValidateTokenResponse.builder()
                .valid(true)
                .accessToken(jwt)
                .userId(Long.valueOf(claims.getSubject()))
                .email(claims.get("email", String.class))
                .role(claims.get("role", String.class))
                .build();
    }

    /**
     * Renueva token: invalida el token opaco anterior y emite un nuevo par
     * JWT + token opaco para el mismo usuario.
     */
    public TokenResponse refreshToken(String opaqueToken) {
        String jwt = tokenStore.getJwt(opaqueToken);

        if (jwt == null) {
            throw new UserBusinessException("Token invalido o expirado", HttpStatus.UNAUTHORIZED);
        }

        Claims claims;
        try {
            claims = jwtService.parseClaims(jwt);
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (Exception e) {
            throw new UserBusinessException("Token invalido", HttpStatus.UNAUTHORIZED);
        }

        Long userId = Long.valueOf(claims.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserBusinessException(
                        "Usuario no encontrado", HttpStatus.NOT_FOUND));

        tokenStore.delete(opaqueToken);
        return issueTokens(user);
    }

    private TokenResponse issueTokens(User user) {
        String jwt = jwtService.generateToken(user);
        String opaqueToken = UUID.randomUUID().toString();
        tokenStore.store(opaqueToken, jwt);

        return TokenResponse.builder()
                .accessToken(opaqueToken)
                .tokenType("Bearer")
                .expiresInMs(tokenStore.getOpaqueTtlMs())
                .build();
    }
}
