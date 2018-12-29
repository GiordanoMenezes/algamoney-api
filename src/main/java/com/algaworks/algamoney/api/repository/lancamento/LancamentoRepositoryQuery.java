/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.lancamento;

import com.algaworks.algamoney.api.model.DTO.LancTotalporCategoriaDTO;
import com.algaworks.algamoney.api.model.DTO.LancTotalporDiaDTO;
import com.algaworks.algamoney.api.model.DTO.LancamentoResumoDTO;
import com.algaworks.algamoney.api.model.DTO.LancamentoporPessoaDTO;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Giordano
 */
public interface LancamentoRepositoryQuery {
    
    public Page<Lancamento> filtrar(LancamentoFilter lancFilter, Pageable pageable);
    
    public Page<LancamentoResumoDTO> resumir(LancamentoFilter lancFilter, Pageable pageable);
    
    public List<LancTotalporCategoriaDTO> totalPorCategoriaPeriodo(LocalDate dataIni,LocalDate dataFim);
    
     public List<LancTotalporDiaDTO> totalPorDiaPeriodo(LocalDate dataIni,LocalDate dataFim);
    
     public List<LancamentoporPessoaDTO> totalPorPessoaPeriodo(LocalDate dataIni,LocalDate dataFim);
}
