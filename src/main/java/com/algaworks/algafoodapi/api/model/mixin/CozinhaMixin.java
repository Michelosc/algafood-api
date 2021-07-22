package com.algaworks.algafoodapi.api.model.mixin;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;

}
