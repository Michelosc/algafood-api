package com.algaworks.algafoodapi.api.openapi.model;

import com.algaworks.algafoodapi.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi extends PageModelOpenApi<CozinhaModel> {
}
