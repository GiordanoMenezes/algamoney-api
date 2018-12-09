/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algaworks.algamoney.api.security.token;

import com.algaworks.algamoney.api.config.property.AlgamoneyApiProperty;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *
 * @author Giordano
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>  {
    
    @Autowired
    AlgamoneyApiProperty algamoneyproperty;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> type) {
         return returnType.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter mp, MediaType mt, Class<? extends HttpMessageConverter<?>> type, ServerHttpRequest req, ServerHttpResponse resp) {

        HttpServletRequest sreq = ((ServletServerHttpRequest) req ).getServletRequest();
        HttpServletResponse sresp = ((ServletServerHttpResponse) resp ).getServletResponse();
        
        DefaultOAuth2AccessToken datoken = (DefaultOAuth2AccessToken) body;
        
        String refreshtoken = body.getRefreshToken().getValue();
        adicionarRefreshTokenNoCookie(refreshtoken,sreq,sresp);
        removerRefreshTokendoBody(datoken);
        return body;
    }

    private void adicionarRefreshTokenNoCookie(String refreshtoken, HttpServletRequest sreq, HttpServletResponse sresp) {
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshtoken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(algamoneyproperty.getSeguranca().isEnableHttps()); 
            refreshTokenCookie.setPath(sreq.getContextPath()+"/oauth/token");
            refreshTokenCookie.setMaxAge(60*60*24*30);
            sresp.addCookie(refreshTokenCookie);
            
    }

    private void removerRefreshTokendoBody(DefaultOAuth2AccessToken datoken) {
        datoken.setRefreshToken(null);
    }
    
}
