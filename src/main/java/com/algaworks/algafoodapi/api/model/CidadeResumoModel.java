package com.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

    @ApiModelProperty(value = "1")
    private Long id;

    @ApiModelProperty(value = "Uberlândia")
    private String nome;

    @ApiModelProperty(value = "Minas Gerais")
    private String estado;

}
