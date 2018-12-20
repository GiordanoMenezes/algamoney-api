/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Giordano
 */
@RestController
@RequestMapping("/tokens")
public class TokenLogoutReosurce {
    
    @Autowired
    AlgamoneyApiProperty algamoneyproperty;
    
    @DeleteMapping("/invalidate")
    public void invalidar(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("refreshToken",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(algamoneyproperty.getSeguranca().isEnableHttps()); 
        cookie.setMaxAge(0);
        cookie.setPath(req.getContextPath()+"/oauth/token");
        
        resp.addCookie(cookie);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
}
