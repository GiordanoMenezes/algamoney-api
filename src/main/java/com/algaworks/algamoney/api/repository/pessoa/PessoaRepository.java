/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.pessoa;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Giordano
 */
public interface PessoaRepository extends JpaRepository<Pessoa,Long>,PessoaRepositoryQuery {
    
    @Override
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

    public List<Pessoa> findAllByOrderByNome();
    
}
