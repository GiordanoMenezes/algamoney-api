/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Usuario;
import com.algaworks.algamoney.api.repository.UsuarioRepository;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private UsuarioRepository usuarioRepository;
    
    @GetMapping("/user/me")
    public ResponseEntity<Usuario> getUsuarioAtual(HttpServletRequest req) {
        Principal principal = req.getUserPrincipal();
        String usuNome = principal.getName();
        Optional<Usuario> usuoptional = usuarioRepository.findByEmail(usuNome);
        Usuario usuario = usuoptional.orElseThrow(() ->new RuntimeException("Info de usuário logado não foram acessíveis."));
        
        return ResponseEntity.ok(usuario);
    }
    
    @DeleteMapping("/invalidate")
    public void invalidar(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("refreshToken",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //TODO: Em producao será true
        cookie.setMaxAge(0);
        cookie.setPath(req.getContextPath()+"/oauth/token");
        
        resp.addCookie(cookie);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
}
