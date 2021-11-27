package com.algaworks.algafoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {

        private Set<String> detinataraios;
        private String assunto;
        private String corpo;
    }
}
