/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.repository.lancamento;

import com.algaworks.algamoney.api.model.DTO.LancamentoResumoDTO;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
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
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancFilter, Pageable pageable) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
        root.fetch("categoria", JoinType.INNER);
        root.fetch("pessoa", JoinType.INNER);

        criteriaQuery.select(root);

        Predicate[] predicados = criarRestricoes(lancFilter, builder, root);

        criteriaQuery.where(predicados);

        if (pageable.getSort() != null) {
            String ordemString = pageable.getSort().toString();
            String[] ordemStringd = StringUtils.split(ordemString, ":");
            String orderfield = ordemStringd[0];
            if ("ASC".equals(ordemStringd[1].trim())) {
                criteriaQuery.orderBy(builder.asc(root.get(orderfield)));
            } else {
                criteriaQuery.orderBy(builder.desc(root.get(orderfield)));
            }
        }

        TypedQuery<Lancamento> query = em.createQuery(criteriaQuery);

        adicionarRestricoesPaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, getTotalRegistros(lancFilter));
    }

    @Override
    public Page<LancamentoResumoDTO> resumir(LancamentoFilter lancFilter, Pageable pageable) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<LancamentoResumoDTO> criteria = builder.createQuery(LancamentoResumoDTO.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(LancamentoResumoDTO.class,
                root.get("id"), root.get("descricao"), root.get("dataVencimento"),
                root.get("dataPagamento"), root.get("valor"), root.get("tipo"),
                root.get("categoria").get("nome"), root.get("pessoa").get("nome")));

        Predicate[] predicados = criarRestricoes(lancFilter, builder, root);

        criteria.where(predicados);

        if (pageable.getSort() != null) {
            String ordemString = pageable.getSort().toString();
            String[] ordemStringd = StringUtils.split(ordemString, ":");
            String orderfield = ordemStringd[0];
            if ("ASC".equals(ordemStringd[1].trim())) {
                criteria.orderBy(builder.asc(root.get(orderfield)));
            } else {
                criteria.orderBy(builder.desc(root.get(orderfield)));
            }
        }

        TypedQuery<LancamentoResumoDTO> query = em.createQuery(criteria);

        adicionarRestricoesPaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, getTotalRegistros(lancFilter));
    }

    private void adicionarRestricoesPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = (paginaAtual) * totalPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalPorPagina);

    }

    private Long getTotalRegistros(LancamentoFilter lancFilter) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicados = criarRestricoes(lancFilter, builder, root);
        criteria.where(predicados);

        criteria.select(builder.count(root));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicados = new ArrayList<>();

        if (!StringUtils.isEmpty(lancFilter.getDescricao())) {
            predicados.add(builder.like(builder.lower(root.get("descricao")), "%" + lancFilter.getDescricao().toLowerCase() + "%"));
        }
        if (lancFilter.getVencimentoDe() != null) {
            predicados.add(builder.greaterThanOrEqualTo(root.<LocalDate>get("dataVencimento"), lancFilter.getVencimentoDe()));
        }
        if (lancFilter.getVencimentoAte() != null) {
            predicados.add(builder.lessThanOrEqualTo(root.<LocalDate>get("dataVencimento"), lancFilter.getVencimentoAte()));
        }

        return predicados.toArray(new Predicate[predicados.size()]);

    }

}
