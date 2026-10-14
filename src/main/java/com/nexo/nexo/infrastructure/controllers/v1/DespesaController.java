package com.nexo.nexo.infrastructure.controllers.v1;

import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.CreateTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.DeleteTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.FindAllTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.FindByIdTransacaoUseCase;
import com.nexo.nexo.domain.useCases.transacao.UpdateTransacaoUseCase;
import com.nexo.nexo.infrastructure.DTOs.DespesaDTO;
import com.nexo.nexo.infrastructure.mappers.DespesaMapper;
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

import java.util.List;

@Tag(name = "Despesa V1", description = "API para gerenciar despesas")
@RestController
@RequestMapping("api/v1/despesa")
public class DespesaController {

    private final CreateTransacaoUseCase createDespesaUseCase;
    private final FindAllTransacaoUseCase findAllDespesaUseCase;
    private final FindByIdTransacaoUseCase findByIdDespesaUseCase;
    private final DeleteTransacaoUseCase deleteDespesaUseCase;
    private final UpdateTransacaoUseCase updateDespesaUseCase;

    public DespesaController(@Qualifier("despesaGatewayImpl") TransacaoGateway transacaoGateway) {
        this.createDespesaUseCase = transacaoGateway::save;
        this.findAllDespesaUseCase = transacaoGateway::findAll;
        this.findByIdDespesaUseCase = transacaoGateway::findById;
        this.deleteDespesaUseCase = transacaoGateway::delete;
        this.updateDespesaUseCase = transacaoGateway::update;
    }

    @Operation(summary = "Buscar despesa por ID", description = "Retorna uma despesa específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("findById/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable @Parameter(description = "Id usado para encontrar a Despesa") Long id) {
        return new ResponseEntity<>(DespesaMapper.toDTO(findByIdDespesaUseCase.execute(id)), HttpStatus.OK);
    }

    @Operation(summary = "Listar todas as despesas", description = "Retorna uma lista de todas as despesas cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("findAll")
    public ResponseEntity<List<DespesaDTO>> findAll() {
        return new ResponseEntity<>(findAllDespesaUseCase.execute().stream().
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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<DespesaDTO> save(@RequestBody @Parameter(description = "Objeto DespesaDTO contendo os dados da despesa a ser criada") DespesaDTO despesaDTO) {
        return new ResponseEntity<>(DespesaMapper.toDTO(createDespesaUseCase.execute(DespesaMapper.toEntity(despesaDTO))), HttpStatus.OK);
    }

    @PatchMapping ("update/{id}")
    @Operation (summary = "Atualizar despesa", description = "Atualiza uma despesa específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<DespesaDTO> update(@PathVariable @Parameter(description = "Id usado para encontrar a Despesa") Long id, @RequestBody @Parameter(description = "Objeto DespesaDTO contendo os dados da despesa a ser atualizada") DespesaDTO despesaDTO) {
            return new ResponseEntity<>(DespesaMapper.toDTO(updateDespesaUseCase.execute(id, DespesaMapper.toEntity(despesaDTO))), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Excluir despesa", description = "Exclui uma despesa específica com base no ID fornecido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Id usado para encontrar a Despesa") Long id) {
        deleteDespesaUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
