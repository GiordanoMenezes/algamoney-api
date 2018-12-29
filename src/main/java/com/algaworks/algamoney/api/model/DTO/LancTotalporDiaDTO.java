/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.model.DTO;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.TipoLancamento;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Giordano
 */
public class LancTotalporDiaDTO {

    private TipoLancamento tipo;

    private LocalDate dia;

    private BigDecimal total;

    public LancTotalporDiaDTO(TipoLancamento tipo, LocalDate dia, BigDecimal total) {
        this.tipo = tipo;
        this.dia = dia;
        this.total = total;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
