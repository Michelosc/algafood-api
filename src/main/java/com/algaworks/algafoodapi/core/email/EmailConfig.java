package com.algaworks.algafoodapi.core.email;

import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            default:
                return null;
        }
    }
}

