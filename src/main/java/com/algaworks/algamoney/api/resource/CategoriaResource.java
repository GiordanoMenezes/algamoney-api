/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Giordano
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository catRepository;

    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read') ")
    public List<Categoria> listAll() {
        return catRepository.findAllByOrderByNomeAsc();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write') ")
    public ResponseEntity<Categoria> addCategoria(@Valid @RequestBody Categoria cat, HttpServletResponse response) {
        Categoria catSalva = catRepository.save(cat);

        //Estamos disparando o evento RecursoCriadoEvent para setarmos a uri da categoria salva no Location do Header
        publisher.publishEvent(new RecursoCriadoEvent(this, response, catSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(catSalva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read') ")
    public ResponseEntity<Categoria> buscaCategoria(@PathVariable Long id) {
           Optional<Categoria> cat = catRepository.findById(id);
           return cat.isPresent() ? ResponseEntity.ok(cat.get()) : ResponseEntity.notFound().build();
    }

}
