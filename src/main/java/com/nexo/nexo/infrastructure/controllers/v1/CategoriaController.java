package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.domain.implementations.categoria.CreateCategoria;
import com.nexo.nexo.domain.useCases.categoria.CreateCategoriaUseCase;
import com.nexo.nexo.domain.useCases.categoria.FindAllCategoriaUseCase;
import com.nexo.nexo.domain.useCases.categoria.FindByIdCategoriaUseCase;
import com.nexo.nexo.domain.useCases.usuario.FindAllUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.FindByIdUsuarioUseCase;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Endpoints para gerenciar categorias")
@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    private final CreateCategoriaUseCase createCategoriaUseCase;
    private final FindAllCategoriaUseCase findAllCategoriaUseCase;
    private final FindByIdCategoriaUseCase findByIdCategoriaUseCase;

    public CategoriaController(CategoriaGateway categoriaGateway) {
        this.createCategoriaUseCase = categoriaGateway::save;
        this.findAllCategoriaUseCase = categoriaGateway::findAll;
        this.findByIdCategoriaUseCase = categoriaGateway::findById;
    }


    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")

    })
    @GetMapping("findAll")
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        return new ResponseEntity<>(findAllCategoriaUseCase.execute().stream()
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
        Categoria categoria = findByIdCategoriaUseCase.execute(id);
        return new ResponseEntity<>(CategoriaMapper.toDTO(categoria), HttpStatus.OK);
    }

    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria com base nos dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("save")
    public ResponseEntity<CategoriaDTO> save(@RequestBody
                                                 @Parameter(description = "Dados da Categoria a ser criada") CategoriaDTO categoriaDTO){
        Categoria categoria = CategoriaMapper.toEntity(categoriaDTO);
        categoria = createCategoriaUseCase.execute(categoria);
        return new ResponseEntity<>(CategoriaMapper.toDTO(categoria), HttpStatus.OK);
    }
}
