package com.nexo.nexo.infrastructure.security;

import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthenticatedUserResolver {
    private AuthenticatedUserResolver() {
    }

    public static Long resolveAuthenticatedUserId(UsuarioRepository usuarioRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UsuarioPersistence usuarioPersistence && usuarioPersistence.getId() != null) {
            return usuarioPersistence.getId();
        }

        return usuarioRepository.findByEmail(authentication.getName())
                .map(UsuarioPersistence::getId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}