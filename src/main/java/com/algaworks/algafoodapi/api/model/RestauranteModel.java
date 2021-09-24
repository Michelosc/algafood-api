package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;
    private Boolean ativo;

}
