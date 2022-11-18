package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.assembler.EstadoInputDisassembler;
import com.algaworks.algafoodapi.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.EstadoModel;
import com.algaworks.algafoodapi.api.v1.model.input.EstadoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Override
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();
        var estados = estadoModelAssembler.toCollectionModel(todosEstados);
        return estados;
    }

    @GetMapping(path = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel buscar(@PathVariable Long estadoId) {
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
        return estadoModelAssembler.toModel(estado);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        estado = cadastroEstado.salvar(estado);
        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping(path = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstado.salvar(estadoAtual);

        return estadoModelAssembler.toModel(estadoAtual);
    }

    @DeleteMapping(path = "/{estadoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstado.excluir(estadoId);
    }
}
