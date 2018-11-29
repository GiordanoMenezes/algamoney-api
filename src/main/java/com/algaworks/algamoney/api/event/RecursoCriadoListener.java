/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.event;

import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Giordano
 */
@Component
public class RecursoCriadoListener  {

    @EventListener
    public void onApplicationEvent(RecursoCriadoEvent e) {
        HttpServletResponse response = e.getResponse();
        Long id = e.getId();
        
         URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(id).toUri();
         response.setHeader("Location",uri.toASCIIString());
    }
    
}
