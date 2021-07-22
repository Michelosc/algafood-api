package com.algaworks.algafoodapi.core.jackson;

import com.algaworks.algafoodapi.api.model.mixin.RestauranteMixin;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }

}
