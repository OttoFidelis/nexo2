package com.nexo.nexo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.algorithms.Algorithm;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {
    private final String secret = "secret";

    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(Usuario usuario) {
        return JWT.create()
                .withClaim("userId", usuario.getId())
                .withSubject(usuario.getEmail().value())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public String validateAndGetSubject(String token) {
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token inválido ou expirado", exception);
        }
    }
}
