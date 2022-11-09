package com.algaworks.algafoodapi.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FormaPagamentoInput {

    @ApiModelProperty(example = "1")
    @NotBlank
    private String descricao;

}
