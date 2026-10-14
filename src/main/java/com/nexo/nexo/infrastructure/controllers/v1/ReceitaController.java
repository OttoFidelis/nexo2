package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.CreateTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.DeleteTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.FindAllTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.FindByIdTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.UpdateTransacaoUseCase;
import com.nexo.nexo.infrastructure.DTOs.ReceitaDTO;
import com.nexo.nexo.infrastructure.mappers.ReceitaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Receita V1", description = "API para gerenciar receitas")
@RestController
@RequestMapping("api/v1/receita")
public class ReceitaController {

    private final CreateTransacaoUseCase  createReceitaUseCase;
    private final FindAllTransacaoUseCase findAllReceitaUseCase;
    private final FindByIdTransacaoUseCase findByIdReceitaUseCase;
    private final UpdateTransacaoUseCase updateReceitaUseCase;
    private final DeleteTransacaoUseCase deleteReceitaUseCase;

    public ReceitaController(@Qualifier("receitaGatewayImpl") TransacaoGateway transacaoGateway) {
        this.createReceitaUseCase = transacaoGateway::save;
        this.findAllReceitaUseCase = transacaoGateway::findAll;
        this.findByIdReceitaUseCase = transacaoGateway::findById;
        this.updateReceitaUseCase = transacaoGateway::update;
        this.deleteReceitaUseCase = transacaoGateway::delete;
    }

    @Operation(summary = "Buscar receita por ID", description = "Retorna uma receita específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("findById/{id}")
    public ResponseEntity<ReceitaDTO> findById(@PathVariable @Parameter(description = "Id usado para encontrar a Receita") Long id) {
        return new ResponseEntity<>(ReceitaMapper.toDTO(findByIdReceitaUseCase.execute(id)), HttpStatus.OK);
    }

    @Operation(summary = "Listar todas as receitas", description = "Retorna uma lista de todas as receitas cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("findAll")
    public ResponseEntity<List<ReceitaDTO>> findAll() {
        return new ResponseEntity<>(findAllReceitaUseCase.execute().stream().
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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReceitaDTO> save(@RequestBody @Parameter(description = "Objeto ReceitaDTO contendo os dados da receita a ser criada") ReceitaDTO receitaDTO) {
        if (receitaDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo da requisição é obrigatório");
        }

        return new ResponseEntity<>(ReceitaMapper.toDTO(createReceitaUseCase.execute(ReceitaMapper.toEntity(receitaDTO))), HttpStatus.OK);
    }

    @PatchMapping ("update/{id}")
    @Operation (summary = "Atualizar receita", description = "Atualiza uma receita específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReceitaDTO> update(@PathVariable @Parameter(description = "Id usado para encontrar a Receita") Long id, @RequestBody @Parameter(description = "Objeto ReceitaDTO contendo os dados da receita a ser atualizada") ReceitaDTO receitaDTO) {
        return new ResponseEntity<>(ReceitaMapper.toDTO(updateReceitaUseCase.execute(id, ReceitaMapper.toEntity(receitaDTO))), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Excluir receita", description = "Exclui uma receita específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receita excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Id usado para encontrar a Receita") Long id) {
        deleteReceitaUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
