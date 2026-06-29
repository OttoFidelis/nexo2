package com.nexo.nexo.domain.gateway;

import com.nexo.nexo.domain.entities.usuario.Usuario;

import java.util.List;

public interface UsuarioGateway {
    public Usuario findById(Long id);
    public Usuario save(Usuario usuario);
    public List<Usuario> findAll();
    public Usuario findByEmail(String email);
    public Usuario login(String email, String senha);
}
