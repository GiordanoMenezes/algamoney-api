/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giordano
 */
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRep;

    public Pessoa atualizaPessoa(Pessoa pessoa, Long id) {
        buscarPessoaPorCodigo(id);
        pessoa.setId(id);
        return pessoaRep.save(pessoa);
    }

    public Pessoa atualizaPessoaAtivo(Boolean ativo, Long id) {
        Pessoa p = buscarPessoaPorCodigo(id);
        p.setAtivo(ativo);
        return pessoaRep.save(p);
    }

    private Pessoa buscarPessoaPorCodigo(Long id) {
        //     Optional<Pessoa> op = pessoaRep.findById(id);
        Pessoa op = pessoaRep.findOne(id);
        //    return op.orElseThrow(()-> new EmptyResultDataAccessException("Id da pessoa passada na requisição não existe na base de dados", 1));       
        if (op == null) {
            throw new EmptyResultDataAccessException("Id da pessoa passada na requisição não existe na base de dados", 1);
        }
        return op;
    }
}
