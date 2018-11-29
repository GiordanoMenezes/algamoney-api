/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.security;

import com.algaworks.algamoney.api.model.Usuario;
import com.algaworks.algamoney.api.repository.UsuarioRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giordano
 */
@Service
public class AppUserDetailsService implements UserDetailsService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<Usuario> usuOptional = usuarioRepository.findByEmail(email);
       Usuario usuario = usuOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou Senha inválidos"));
       return new User(email,usuario.getSenha(),getPermissoes(usuario));
    
}

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
       Set<SimpleGrantedAuthority> authorities = new HashSet<>();
       usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao())));
       return authorities;
    }
    
}