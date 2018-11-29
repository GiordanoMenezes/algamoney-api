/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.filter;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Giordano
 */
public class LancamentoFilter {
    
    private String descricao;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimentoDe;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimentoAte;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getVencimentoDe() {
        return vencimentoDe;
    }

    public void setVencimentoDe(LocalDate vencimentoDe) {
        this.vencimentoDe = vencimentoDe;
    }

    public LocalDate getVencimentoAte() {
        return vencimentoAte;
    }

    public void setVencimentoAte(LocalDate vencimentoAte) {
        this.vencimentoAte = vencimentoAte;
    }
    
    
}
