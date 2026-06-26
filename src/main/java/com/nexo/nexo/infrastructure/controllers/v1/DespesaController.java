package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.DTOs.DespesaDTO;
import com.nexo.nexo.infrastructure.mappers.DespesaMapper;
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

@Tag(name = "Despesa", description = "API para gerenciar despesas")
@RestController
@RequestMapping("api/v1/despesa")
public class DespesaController {

    private final TransacaoGateway transacaoGateway;

    public DespesaController(@Qualifier("despesaGatewayImpl") TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    @Operation(summary = "Buscar despesa por ID", description = "Retorna uma despesa específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"
            )
    })
    @GetMapping("findById/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable @Parameter(description = "Id usado para encontrar a Despesa") Long id) {
        return new ResponseEntity<>(DespesaMapper.toDTO(transacaoGateway.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Listar todas as despesas", description = "Retorna uma lista de todas as despesas cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("findAll")
    public ResponseEntity<List<DespesaDTO>> findAll() {
        return new ResponseEntity<>(transacaoGateway.findAll().stream().
                map(DespesaMapper::toDTO).
                toList(), HttpStatus.OK);
    }

    @PostMapping("save")
    @Operation(summary = "Criar nova despesa", description = "Cria uma nova despesa com base nos dados fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<DespesaDTO> save(@RequestBody @Parameter(description = "Objeto DespesaDTO contendo os dados da despesa a ser criada") DespesaDTO despesaDTO) {
        return new ResponseEntity<>(DespesaMapper.toDTO(transacaoGateway.save(DespesaMapper.toEntity(despesaDTO))), HttpStatus.OK);
    }
}
