/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Giordano
 */
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    public List<Categoria> findAllByOrderByNomeAsc();
    
}
