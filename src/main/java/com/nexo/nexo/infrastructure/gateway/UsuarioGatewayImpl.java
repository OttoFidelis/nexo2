package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.config.TokenConfig;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.infrastructure.mappers.UsuarioMapper;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioGatewayImpl implements UsuarioGateway {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public UsuarioGatewayImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @Override
    public Usuario findById(Long id) {
        return UsuarioMapper.toEntity(usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")));
    }

    @Override
    public Usuario save(Usuario usuario) {
        String senhaDigitada = usuario.getSenha().value();
        String senhaCriptografada = passwordEncoder.encode(senhaDigitada);
        usuario.setSenha(new Senha(senhaCriptografada));
        return UsuarioMapper.toEntity(usuarioRepository.save(UsuarioMapper.toPersistence(usuario)));
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toEntity)
                .toList();
    }

    @Override
    public Usuario findByEmail(String email) {
        return UsuarioMapper.toEntity(usuarioRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")));
    }

    @Override
    public String login(String email, String senha) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, senha);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario != null) {
            String token = tokenConfig.generateToken(usuario);
            return token;
        }
        else {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
    }

    @Override
    public Usuario updateSenha(Long id, String senha) {
        Usuario usuario = findById(id);
        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenha(new Senha(senhaCriptografada));
        return UsuarioMapper.toEntity(usuarioRepository.save(UsuarioMapper.toPersistence(usuario)));
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
