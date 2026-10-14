package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.DTOs.ReceitaDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioResumoDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReceitaMapperTest {

    @Test
    void shouldRejectNullReceitaDto() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ReceitaMapper.toEntity((ReceitaDTO) null)
        );

        assertEquals("Receita não pode ser nula", exception.getMessage());
    }

    @Test
    void shouldRejectMissingCategory() {
        ReceitaDTO dto = new ReceitaDTO(
                null,
                "Salário",
                new BigDecimal("2500.00"),
                LocalDate.now(),
                null
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ReceitaMapper.toEntity(dto)
        );

        assertEquals("Categoria da receita é obrigatória", exception.getMessage());
    }

    @Test
        void shouldMapCategoryWithUserIdOnly() {
        CategoriaDTO categoriaDTO = new CategoriaDTO(
                1L,
                "Salário",
                "Receita fixa",
                new UsuarioResumoDTO(12L, null, null)
        );

                assertEquals(12L, CategoriaMapper.toEntity(categoriaDTO).getUsuario().getId());
    }
}
