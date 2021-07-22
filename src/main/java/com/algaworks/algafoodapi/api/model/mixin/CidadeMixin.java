package com.algaworks.algafoodapi.api.model.mixin;

import com.algaworks.algafoodapi.core.validation.Groups;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
