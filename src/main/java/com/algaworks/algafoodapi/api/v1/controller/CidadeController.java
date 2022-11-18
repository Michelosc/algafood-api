package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.ResourceUriHelper;
import com.algaworks.algafoodapi.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafoodapi.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.CidadeModel;
import com.algaworks.algafoodapi.api.v1.model.input.CidadeInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @GetMapping(path ="/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel buscar(
            @PathVariable Long cidadeId) {

        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cadastroCidade.salvar(cidade);

            CidadeModel  cidadeModel = cidadeModelAssembler.toModel(cidade);
            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());
            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(path ="/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel atualizar(
            @PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }


}
