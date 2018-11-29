/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Giordano
 */
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    
}
