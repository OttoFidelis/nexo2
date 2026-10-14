package com.nexo.nexo.domain.entities.usuario;

public class Usuario {
    private Long id;
    private NomeUsuario nome;
    private Email email;
    private Senha senha;
    public Usuario() {
    }
    public Usuario(Long id) {
        this.id = normalizeId(id);
    }
    public Usuario(Long id, NomeUsuario nome, Email email, Senha senha) {
        this.id = normalizeId(id);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = normalizeId(id);
    }

    private Long normalizeId(Long id) {
        return id != null && id > 0 ? id : null;
    }
    public NomeUsuario getNome() {
        return nome;
    }
    public void setNome(NomeUsuario nome) {}
    public Email getEmail() {
        return email;
    }
    public void setEmail(Email email) {
        this.email = email;
    }
    public Senha getSenha() {
        return senha;
    }
    public void setSenha(Senha senha) {
        this.senha = senha;
    }
}
