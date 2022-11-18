package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel =
                formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
                        .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId,"associar"));

        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel
                    .add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao
                            (restauranteId, formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentoModel;
    }

    @DeleteMapping(path = "/{formaPagamentoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{formaPagamentoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

}
