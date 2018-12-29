/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.model.DTO;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.model.TipoLancamento;
import java.math.BigDecimal;

/**
 *
 * @author Giordano
 */
public class LancamentoporPessoaDTO {

    private TipoLancamento tipo;

    private Pessoa pessoa;

    private BigDecimal total;

    public LancamentoporPessoaDTO(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.total = total;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

 

}
