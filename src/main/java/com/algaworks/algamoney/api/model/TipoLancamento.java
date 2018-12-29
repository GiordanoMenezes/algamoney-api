/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.model;

/**
 *
 * @author Giordano
 */
public enum TipoLancamento {

    RECEITA("Receita"),
    DESPESA("Despesa");
    
    private String descricao;

    private TipoLancamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }  
    
}
