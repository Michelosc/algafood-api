package com.algaworks.algafoodapi.api.model;

import com.algaworks.algafoodapi.domain.model.Cidade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

    @ApiModelProperty(value = "1")
    private Long id;

    @ApiModelProperty(value = "Uberlândia")
    private String nome;

    @ApiModelProperty(value = "Minas Gerais")
    private String estado;

}
