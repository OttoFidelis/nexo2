package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Endpoints para gerenciar categorias")
@RestController
@RequestMapping("api/V1/categorias")
public class CategoriaController {

    private final CategoriaGateway categoriaGateway;

    public CategoriaController(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")

    })
    @GetMapping("findAll")
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        return new ResponseEntity<>(categoriaGateway.findAll().stream()
                .map(CategoriaMapper::toDTO)
                .toList(),  HttpStatus.OK);

    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("findById")
    public ResponseEntity<CategoriaDTO> findById(@RequestParam @Parameter(description = "Id usado para encontrar a Categoria") Long id){
        Categoria categoria = categoriaGateway.findById(id);
        return new ResponseEntity<>(CategoriaMapper.toDTO(categoria), HttpStatus.OK);
    }

    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria com base nos dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "20                                                                                                                                                                    0", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("save")
    public ResponseEntity<CategoriaDTO> save(@RequestBody @Parameter(description = "Dados da Categoria a ser criada") CategoriaDTO categoriaDTO){
        Categoria categoria = CategoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaGateway.save(categoria);
        return new ResponseEntity<>(CategoriaMapper.toDTO(categoria), HttpStatus.OK);
    }
}
