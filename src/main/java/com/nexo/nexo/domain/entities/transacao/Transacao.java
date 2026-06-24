package com.nexo.nexo.domain.entities.transacao;

import java.time.LocalDate;

import com.nexo.nexo.domain.entities.valueObjects.Descricao;

public class Transacao {
    private Long id;
    private Descricao descricao;
    private Valor valor;
    private LocalDate data;
    private String categoria;
    private Tipo tipo;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Descricao getDescricao() {
        return descricao;
    }
    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }
    public Valor getValor() {
        return valor;
    }
    public void setValor(Valor valor) {
        this.valor = valor;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    public Transacao(Long id, Descricao descricao, Valor valor, LocalDate data, String categoria, Tipo tipo) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }
    
    
    
}