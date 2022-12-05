package com.algaworks.algafoodapi.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecatipnHandler implements HandlerInterceptor {
    // Classe e método para avisar usuários da API que recurso será desativado.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-AlgaFood-Deprecated", "Essa versão da api está depreciada e deixará de " +
                    "existir a partir de 01/01/2023. Use a versão mais atual da API.");
        }
        return true;
    }
}
