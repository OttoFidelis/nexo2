package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.categoria.Nome;
import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.entities.transacao.Valor;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.DespesaRepository;
import com.nexo.nexo.infrastructure.repositories.ReceitaRepository;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoGatewayImplTest {

    @Mock
    private DespesaRepository despesaRepository;

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

        @Mock
        private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DespesaGatewayImpl despesaGateway;

    @InjectMocks
    private ReceitaGatewayImpl receitaGateway;

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSaveDespesaWithCategoryAndUserIdsOnly() {
        CategoriaPersistence categoriaPersistence = new CategoriaPersistence(
                1L,
                "Alimentação",
                "Gastos com alimentação",
                new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123")
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(categoriaPersistence.getUsuario(), null, categoriaPersistence.getUsuario().getAuthorities())
        );
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaPersistence));
        when(usuarioRepository.findById(12L)).thenReturn(Optional.of(new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123")));
        when(despesaRepository.save(any(DespesaPersistence.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transacao despesa = new Transacao(
                null,
                new Descricao("Compra no mercado"),
                new Valor(new BigDecimal("120.50")),
                LocalDate.of(2026, 10, 14),
                new Categoria(1L, new Nome("Alimentação"), new Descricao("Gastos com alimentação"), new Usuario(12L)),
                Tipo.DESPESA
        );

        Transacao saved = despesaGateway.save(despesa);

        assertEquals(1L, saved.getCategoria().getId());
        assertEquals(12L, saved.getCategoria().getUsuario().getId());
    }

    @Test
    void shouldSaveReceitaWithCategoryAndUserIdsOnly() {
        CategoriaPersistence categoriaPersistence = new CategoriaPersistence(
                2L,
                "Vendas",
                "Receitas relacionadas a vendas de produtos",
            new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123")
        );
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(categoriaPersistence.getUsuario(), null, categoriaPersistence.getUsuario().getAuthorities())
        );
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoriaPersistence));
        when(usuarioRepository.findById(12L)).thenReturn(Optional.of(new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123")));
        when(receitaRepository.save(any(ReceitaPersistence.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transacao receita = new Transacao(
                null,
                new Descricao("Venda online"),
                new Valor(new BigDecimal("250.00")),
                LocalDate.of(2026, 10, 14),
                new Categoria(2L, new Nome("Vendas"), new Descricao("Receitas relacionadas a vendas de produtos"), new Usuario(12L)),
                Tipo.RECEITA
        );

        Transacao saved = receitaGateway.save(receita);

        assertEquals(2L, saved.getCategoria().getId());
        assertEquals(12L, saved.getCategoria().getUsuario().getId());
    }

        @Test
        void shouldRejectDespesaCreationForDifferentUser() {
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence(12L, "Maria", "maria@example.com", "senha123");
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(usuarioPersistence, null, usuarioPersistence.getAuthorities())
        );
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(new CategoriaPersistence(
            1L,
            "Alimentação",
            "Gastos com alimentação",
            new UsuarioPersistence(11L, "João", "joao@example.com", "senha123")
        )));

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> despesaGateway.save(new Transacao(
                null,
                new Descricao("Compra no mercado"),
                new Valor(new BigDecimal("120.50")),
                LocalDate.of(2026, 10, 14),
                new Categoria(1L, new Nome("Alimentação"), new Descricao("Gastos com alimentação"), new Usuario(11L)),
                Tipo.DESPESA
            ))
        );

        assertEquals("Usuário não autorizado para criar esta despesa", exception.getMessage());
        }
}