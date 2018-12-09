/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.DTO.LancamentoResumoDTO;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.lancamento.LancamentoRepository;
import com.algaworks.algamoney.api.service.LancamentoService;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Giordano
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancRepository;

    @Autowired
    private LancamentoService lancService;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
    public Page<Lancamento> filtrar(LancamentoFilter lancFilter, Pageable pageable) {
        return lancRepository.filtrar(lancFilter, pageable);
    }
    
    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
    public Page<LancamentoResumoDTO> resumir(LancamentoFilter lancFilter, Pageable pageable) {
        return lancRepository.resumir(lancFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
    public ResponseEntity<Lancamento> buscaLancamento(@PathVariable Long id) {
           Optional<Lancamento> lanc = lancRepository.findById(id);
           return lanc.isPresent() ? ResponseEntity.ok(lanc.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write') ")
    public ResponseEntity<Lancamento> addLancamento(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancSalvo = lancService.novoLancamento(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancSalvo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write') ")
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
          lancRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
   
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write') ")
    public ResponseEntity<Lancamento> atualizaLancamento(@Valid @RequestBody Lancamento lancamento, @PathVariable Long id) {
        Lancamento lancamentoSalvo = lancService.atualizaLancamento(lancamento, id);
        return ResponseEntity.ok(lancamentoSalvo);
    }

}
