package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.categoria.Nome;
import com.nexo.nexo.domain.entities.usuario.Email;
import com.nexo.nexo.domain.entities.usuario.NomeUsuario;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaGatewayImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CategoriaGatewayImpl categoriaGateway;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAllowOwnerToCreateCategory() {
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioPersistence, null, usuarioPersistence.getAuthorities())
        );
        when(usuarioRepository.findById(12L)).thenReturn(Optional.of(usuarioPersistence));
        when(categoriaRepository.save(any(CategoriaPersistence.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria categoria = new Categoria(
                null,
                new Nome("Vendas"),
                new Descricao("Receitas relacionadas a vendas de produtos"),
                new Usuario(12L)
        );

        Categoria saved = categoriaGateway.save(categoria);

        assertEquals(12L, saved.getUsuario().getId());
    }

    @Test
    void shouldRejectCategoryCreationForDifferentUser() {
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioPersistence, null, usuarioPersistence.getAuthorities())
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoriaGateway.save(new Categoria(
                        null,
                        new Nome("Vendas"),
                        new Descricao("Receitas relacionadas a vendas de produtos"),
                        new Usuario(11L)
                ))
        );

        assertEquals("Usuário não autorizado para criar esta categoria", exception.getMessage());
    }
}