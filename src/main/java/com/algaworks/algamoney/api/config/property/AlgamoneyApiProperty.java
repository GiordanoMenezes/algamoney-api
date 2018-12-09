/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author Giordano
 */
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private final Seguranca seguranca  = new Seguranca();

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public static class Seguranca {
        
        private String originPermitida = "http://localhost:4200";

        private boolean enableHttps;

        public String getOriginPermitida() {
            return originPermitida;
        }

        public void setOriginPermitida(String originPermitida) {
            this.originPermitida = originPermitida;
        }
        
        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
        
    }

}
