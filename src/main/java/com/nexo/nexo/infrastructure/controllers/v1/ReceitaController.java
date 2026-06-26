package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.DTOs.ReceitaDTO;
import com.nexo.nexo.infrastructure.mappers.ReceitaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Receita", description = "API para gerenciar receitas")
@RestController
@RequestMapping("api/v1/receita")
public class ReceitaController {

    private final TransacaoGateway transacaoGateway;

    public ReceitaController(@Qualifier("receitaGatewayImpl") TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    @Operation(summary = "Buscar receita por ID", description = "Retorna uma receita específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"
            )
    })
    @GetMapping("findById/{id}")
    public ResponseEntity<ReceitaDTO> findById(@PathVariable @Parameter(description = "Id usado para encontrar a Receita") Long id) {
        return new ResponseEntity<>(ReceitaMapper.toDTO(transacaoGateway.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Listar todas as receitas", description = "Retorna uma lista de todas as receitas cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("findAll")
    public ResponseEntity<List<ReceitaDTO>> findAll() {
        return new ResponseEntity<>(transacaoGateway.findAll().stream().
                map(ReceitaMapper::toDTO).
                toList(), HttpStatus.OK);
    }

    @PostMapping("save")
    @Operation(summary = "Criar nova receita", description = "Cria uma nova receita com base nos dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ReceitaDTO> save(@RequestBody @Parameter(description = "Objeto ReceitaDTO contendo os dados da receita a ser criada") ReceitaDTO receitaDTO) {
        return new ResponseEntity<>(ReceitaMapper.toDTO(transacaoGateway.save(ReceitaMapper.toEntity(receitaDTO))), HttpStatus.OK);
    }
}
