package com.algaworks.algafoodapi.api.v2.model.input;

import com.algaworks.algafoodapi.api.v1.model.input.EstadoIdInput;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@ApiModel("CidadeInput")
@Getter
@Setter
public class CidadeInputV2 {

    @NotBlank
    private String nomeCidade;

    @Valid
    @NotNull
    private Long idEstado;

}
