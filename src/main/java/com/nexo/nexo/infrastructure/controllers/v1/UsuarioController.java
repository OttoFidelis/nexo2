package com.nexo.nexo.infrastructure.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.useCases.usuario.CreateUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.DeleteUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.FindAllUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.FindByEmailUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.FindByIdUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.LoginUsuarioUseCase;
import com.nexo.nexo.domain.useCases.usuario.UpdateSenhaUsuarioUseCase;
import com.nexo.nexo.infrastructure.DTOs.LoginDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTOResponse;
import com.nexo.nexo.infrastructure.mappers.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag (name = "Usuario V1", description = "Endpoints para gerenciar usuários")
@RestController 
@RequestMapping ("/api/v1/usuario")
public class UsuarioController {
    private final LoginUsuarioUseCase loginUsuarioUseCase;
    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final FindAllUsuarioUseCase findAllUsuarioUseCase;
    private final FindByIdUsuarioUseCase findByIdUsuarioUseCase;
    private final UpdateSenhaUsuarioUseCase updateSenhaUsuarioUseCase;
    private final DeleteUsuarioUseCase deleteUsuarioUseCase;
    private final FindByEmailUsuarioUseCase findByEmailUsuarioUseCase;

    public UsuarioController(LoginUsuarioUseCase loginUsuarioUseCase, FindByEmailUsuarioUseCase findByEmailUsuarioUseCase, CreateUsuarioUseCase createUsuarioUseCase, FindAllUsuarioUseCase findAllUsuarioUseCase, FindByIdUsuarioUseCase findByIdUsuarioUseCase, UpdateSenhaUsuarioUseCase updateSenhaUsuarioUseCase, DeleteUsuarioUseCase deleteUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.createUsuarioUseCase = createUsuarioUseCase;
        this.findAllUsuarioUseCase = findAllUsuarioUseCase;
        this.findByIdUsuarioUseCase = findByIdUsuarioUseCase;
        this.updateSenhaUsuarioUseCase = updateSenhaUsuarioUseCase;
        this.deleteUsuarioUseCase = deleteUsuarioUseCase;
        this.findByEmailUsuarioUseCase = findByEmailUsuarioUseCase;
    }

    @Operation (summary = "Login de usuário", description = "Realiza o login de um usuário com base no email e senha fornecidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTOResponse> login(@RequestBody LoginDTO loginDTO) {
        String token = loginUsuarioUseCase.login(loginDTO.email(), loginDTO.senha());
        Usuario usuario = findByEmailUsuarioUseCase.execute(loginDTO.email());
        return new ResponseEntity<>(UsuarioMapper.toDTOResponse(usuario, token), HttpStatus.OK);
    }


    @Operation (summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteUsuario(@RequestBody Long id) {
        deleteUsuarioUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
