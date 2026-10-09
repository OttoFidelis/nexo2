package com.nexo.nexo.domain.entities.categoria;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;

public class Categoria {
    private Long id;
    private Nome nome;
    private Descricao descricao;
    private Usuario usuario;

    public Categoria(Long id, Nome nome, Descricao descricao, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nome getNome() {
        return nome;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
