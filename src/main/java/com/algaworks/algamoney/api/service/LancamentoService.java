/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.lancamento.LancamentoRepository;
import com.algaworks.algamoney.api.repository.pessoa.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.LancamentoPessoaInativaException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giordano
 */
@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRep;

    @Autowired
    private PessoaRepository pessoaRep;

    public Lancamento atualizaLancamento(Lancamento lanc, Long id) {
        buscarLancamentoPorCodigo(id);
        lanc.setCodigo(id);
        return lancamentoRep.save(lanc);
    }

    private Lancamento buscarLancamentoPorCodigo(Long id) {
            Optional<Lancamento> op = lancamentoRep.findById(id);
            return op.orElseThrow(()-> new EmptyResultDataAccessException("Id do lançamento passado na requisição não existe na base de dados", 1));       
    }

    public Lancamento novoLancamento(Lancamento lancto) {
             Optional<Pessoa> opessoa = pessoaRep.findById(lancto.getPessoa().getCodigo());
             Pessoa pessoa = opessoa.orElseThrow(() -> new LancamentoPessoaInativaException("Lançamento com pessoa inexistente."));
        if (!pessoa.getAtivo()) {
            throw new LancamentoPessoaInativaException("Pessoa inativa para Lançamento.");
        }
        return lancamentoRep.save(lancto);
    }
}
