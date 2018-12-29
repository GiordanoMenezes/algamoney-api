/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.DTO.LancamentoporPessoaDTO;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.lancamento.LancamentoRepository;
import com.algaworks.algamoney.api.repository.pessoa.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.LancamentoPessoaInativaException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
    
    public byte[] repLancporPessoa(LocalDate dataIni, LocalDate dataFim) throws JRException {
        List<LancamentoporPessoaDTO> dados = lancamentoRep.totalPorPessoaPeriodo(dataIni, dataFim);
        
        Map<String, Object> parametros = new HashMap<>();
        
        parametros.put("DT_INICIO",Date.valueOf(dataIni));
        parametros.put("DT_FIM",Date.valueOf(dataFim));
        parametros.put("REPORT_LOCALE",new Locale("pt","BR"));
        
        InputStream inputStream = this.getClass().getResourceAsStream("/Reports/LancsPorPessoa.jasper");
        
        
        JasperPrint jasperprint = JasperFillManager.fillReport(inputStream, parametros,
                new JRBeanCollectionDataSource(dados));
        
        return JasperExportManager.exportReportToPdf(jasperprint);
    }
}
