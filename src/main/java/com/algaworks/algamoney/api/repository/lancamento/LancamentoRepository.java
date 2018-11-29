/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.lancamento;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Giordano
 */
public interface LancamentoRepository extends JpaRepository<Lancamento,Long>,LancamentoRepositoryQuery {
    
  //  @Override
 //   @EntityGraph(value = "Lancamentocatpessoa", type = EntityGraph.EntityGraphType.LOAD)
//    @Query(value="SELECT * FROM Categoria",nativeQuery = true)
//     @Query("SELECT c FROM Categoria c JOIN FETCH c.produtos")
    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancFilter, Pageable pageable);
}
