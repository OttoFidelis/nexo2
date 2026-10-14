package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.categoria.Nome;
import com.nexo.nexo.domain.entities.usuario.Email;
import com.nexo.nexo.domain.entities.usuario.NomeUsuario;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioResumoDTO;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriaMapperTest {

    @Test
    void shouldMapCategoryDtoWithUserIdOnly() {
        CategoriaDTO dto = new CategoriaDTO(
                1L,
                "Vendas",
                "Receitas relacionadas a vendas de produtos",
            new UsuarioResumoDTO(12L, null, null)
        );

        Categoria categoria = CategoriaMapper.toEntity(dto);

        assertEquals(1L, categoria.getId());
        assertEquals("Vendas", categoria.getNome().value());
        assertEquals("Receitas relacionadas a vendas de produtos", categoria.getDescricao().value());
        assertEquals(12L, categoria.getUsuario().getId());
    }

        @Test
        void shouldMapCategoriaToSafeDtoWithoutPassword() {
        Categoria categoria = new Categoria(
            3L,
            new Nome("Vendas"),
            new Descricao("Receitas relacionadas a vendas de produtos"),
            new Usuario(
                12L,
                new NomeUsuario("Maria"),
                new Email("maria@example.com"),
                new Senha("senha12345")
            )
        );

        CategoriaDTO dto = CategoriaMapper.toDTO(categoria);

        assertEquals(3L, dto.id());
        assertEquals(12L, dto.usuario().id());
        assertEquals("Maria", dto.usuario().nome());
        assertEquals("maria@example.com", dto.usuario().email());
        }
}