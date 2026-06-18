package com.relatos.users_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Almacen efimero (Redis) que mantiene la relacion entre el token opaco
 * entregado al cliente y el JWT real (phantom token pattern).
 */
@Service
public class TokenStoreService {

    private static final String PREFIX = "opaque:";

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.opaque-ttl-ms}")
    private long opaqueTtlMs;

    public TokenStoreService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(String opaqueToken, String jwt) {
        redisTemplate.opsForValue().set(PREFIX + opaqueToken, jwt, Duration.ofMillis(opaqueTtlMs));
    }

    public String getJwt(String opaqueToken) {
        return redisTemplate.opsForValue().get(PREFIX + opaqueToken);
    }

    public void delete(String opaqueToken) {
        redisTemplate.delete(PREFIX + opaqueToken);
    }

    public long getOpaqueTtlMs() {
        return opaqueTtlMs;
    }
}
