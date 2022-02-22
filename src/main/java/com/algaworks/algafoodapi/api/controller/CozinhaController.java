package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafoodapi.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.api.model.input.CozinhaInput;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());

        Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());

        return cozinhasModelPage;
    }

    @GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
        return cozinhaModelAssembler.toModel(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinha.excluir(cozinhaId);
    }
}
