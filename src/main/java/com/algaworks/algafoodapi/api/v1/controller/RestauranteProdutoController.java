package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.assembler.ProdutoInputDisassembler;
import com.algaworks.algafoodapi.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.ProdutoModel;
import com.algaworks.algafoodapi.api.v1.model.input.ProdutoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroProdutoService;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                                @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if(incluirInativos) {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        }

        return produtoModelAssembler.toCollectionModel(todosProdutos)
                .add(algaLinks.linkToProdutos(restauranteId));
    }

    @GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);
        return produtoModelAssembler.toModel(produto);
    }

    @PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId ,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProduto.salvar(produtoAtual);
        return produtoModelAssembler.toModel(produtoAtual);
    }

}
