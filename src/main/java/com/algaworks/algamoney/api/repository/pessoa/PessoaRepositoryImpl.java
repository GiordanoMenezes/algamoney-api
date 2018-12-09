/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.pessoa;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

/**
 *
 * @author Giordano
 */
public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);

        Root<Pessoa> root = criteriaQuery.from(Pessoa.class);

        criteriaQuery.select(root);

        Predicate[] predicados = criarRestricoes(pessoaFilter, builder, root);

        criteriaQuery.where(predicados);

        if (pageable.getSort() != null && !"UNSORTED".equals(pageable.getSort().toString())) {
            String ordemString = pageable.getSort().toString();
            String[] ordemStringd = StringUtils.split(ordemString, ":");
            String orderfield = ordemStringd[0];
            if ("ASC".equals(ordemStringd[1].trim())) {
                criteriaQuery.orderBy(builder.asc(root.get(orderfield)));
            } else {
                criteriaQuery.orderBy(builder.desc(root.get(orderfield)));
            }
        }

        TypedQuery<Pessoa> query = em.createQuery(criteriaQuery);

        adicionarRestricoesPaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, getTotalRegistros(pessoaFilter));
    }

    private void adicionarRestricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = (paginaAtual) * totalPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalPorPagina);

    }

    private Long getTotalRegistros(PessoaFilter pessoaFilter) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicados = criarRestricoes(pessoaFilter, builder, root);
        criteria.where(predicados);

        criteria.select(builder.count(root));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {

        List<Predicate> predicados = new ArrayList<>();

        if (!StringUtils.isEmpty(pessoaFilter.getNome())) {
            predicados.add(builder.like(builder.lower(root.get("nome")), "%" + pessoaFilter.getNome().toLowerCase() + "%"));
        }
        if (pessoaFilter.getAtivo()!=null) {
            predicados.add(builder.equal(root.<Boolean>get("ativo"),pessoaFilter.getAtivo()));
        }

        return predicados.toArray(new Predicate[predicados.size()]);

    }

}
