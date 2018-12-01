/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Giordano
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read') ")
    public List<Pessoa> listAll() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write') ")
    public ResponseEntity<Pessoa> addPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pesSalva = pessoaRepository.save(pessoa);

        //Estamos disparando o evento RecursoCriadoEvent para setarmos a uri da pessoa salva no Location do Header  
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pesSalva.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pesSalva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read') ")
    public ResponseEntity<Pessoa> buscaPessoa(@PathVariable Long id) {
         Optional<Pessoa> pessoa = pessoaRepository.findById(id);
         return !pessoa.isPresent()?ResponseEntity.notFound().build():ResponseEntity.ok(pessoa.get());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write') ")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluiPessoa(@PathVariable Long id) {
             pessoaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write') ")
    public ResponseEntity<Pessoa> atualizaPessoa(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id) {
        Pessoa pessoaSalva = pessoaService.atualizaPessoa(pessoa, id);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{id}/ativo")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write') ")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizaAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        pessoaService.atualizaPessoaAtivo(ativo, id);
    }

}
