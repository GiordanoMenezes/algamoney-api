/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.service.exception;

/**
 *
 * @author Giordano
 */
public class LancamentoPessoaInativaException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public LancamentoPessoaInativaException(String message) {
        super(message);
    }

    public LancamentoPessoaInativaException(String message, Throwable cause) {
        super(message, cause);
    }
  
    
    
}
