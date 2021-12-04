package com.algaworks.algafoodapi.infrastructure.service.email;

import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDetinatarios(), corpo);
    }
}
