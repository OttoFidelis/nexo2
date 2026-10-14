package com.nexo.nexo.infrastructure.controllers.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.nexo.nexo.config.TokenConfig;
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
import com.nexo.nexo.infrastructure.services.SaldoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    private final SaldoService saldoService;
    private final TokenConfig tokenConfig;

    public UsuarioController(LoginUsuarioUseCase loginUsuarioUseCase, FindByEmailUsuarioUseCase findByEmailUsuarioUseCase, CreateUsuarioUseCase createUsuarioUseCase, FindAllUsuarioUseCase findAllUsuarioUseCase, FindByIdUsuarioUseCase findByIdUsuarioUseCase, UpdateSenhaUsuarioUseCase updateSenhaUsuarioUseCase, DeleteUsuarioUseCase deleteUsuarioUseCase, SaldoService saldoService, TokenConfig tokenConfig) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
        this.createUsuarioUseCase = createUsuarioUseCase;
        this.findAllUsuarioUseCase = findAllUsuarioUseCase;
        this.findByIdUsuarioUseCase = findByIdUsuarioUseCase;
        this.updateSenhaUsuarioUseCase = updateSenhaUsuarioUseCase;
        this.deleteUsuarioUseCase = deleteUsuarioUseCase;
        this.findByEmailUsuarioUseCase = findByEmailUsuarioUseCase;
        this.saldoService = saldoService;
        this.tokenConfig = tokenConfig;
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
        return new ResponseEntity<>(UsuarioMapper.toDTOResponse(usuario, token, saldoService.gerarSaldo(usuario.getId())), HttpStatus.OK);
    }


    @Operation (summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        deleteUsuarioUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @Operation (summary ="Criar usuário", description = "Cria um novo usuário com base nas informações fornecidas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTOResponse> createUsuario(@RequestBody Usuario createUsuarioDTO) {
        Usuario usuario = createUsuarioUseCase.execute(createUsuarioDTO);
        String token = tokenConfig.generateToken(usuario);
        return new ResponseEntity<>(UsuarioMapper.toDTOResponse(usuario, token, saldoService.gerarSaldo(usuario.getId())), HttpStatus.CREATED);
    }

    @Operation (summary ="Buscar usuário por ID", description = "Busca um usuário específico com base no ID fornecido")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuarioDTOResponse> findById(@PathVariable Long id) {
        Usuario usuario = findByIdUsuarioUseCase.execute(id);
        String token = tokenConfig.generateToken(usuario);
        return new ResponseEntity<>(UsuarioMapper.toDTOResponse(usuario, token, saldoService.gerarSaldo(usuario.getId())), HttpStatus.OK);
    }
    
    @Operation (summary ="Listar todos os usuários", description = "Lista todos os usuários cadastrados")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioDTOResponse>> findAll() {
        var usuarios = findAllUsuarioUseCase.execute();
        var usuarioDTOs = usuarios.stream()
                .map(usuario -> UsuarioMapper.toDTOResponse(usuario, tokenConfig.generateToken(usuario), saldoService.gerarSaldo(usuario.getId())))
                .toList();
        return new ResponseEntity<>(usuarioDTOs, HttpStatus.OK);
    }

    @Operation (summary ="Atualizar senha do usuário", description = "Atualiza a senha de um usuário específico")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping ("/updateSenha/{id}")
    public ResponseEntity<UsuarioDTOResponse> updateSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        Usuario usuarioAtualizado = updateSenhaUsuarioUseCase.execute(id, novaSenha);
        String token = tokenConfig.generateToken(usuarioAtualizado);
        return new ResponseEntity<>(UsuarioMapper.toDTOResponse(usuarioAtualizado, token, saldoService.gerarSaldo(usuarioAtualizado.getId())), HttpStatus.OK);
    }
}
