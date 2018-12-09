/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.lancamento;

import com.algaworks.algamoney.api.model.DTO.LancamentoResumoDTO;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Giordano
 */
public interface LancamentoRepositoryQuery {
    
    public Page<Lancamento> filtrar(LancamentoFilter lancFilter, Pageable pageable);
    
    public Page<LancamentoResumoDTO> resumir(LancamentoFilter lancFilter, Pageable pageable);
}
