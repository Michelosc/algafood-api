package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafoodapi.api.model.PermissaoModel;
import com.algaworks.algafoodapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);
    }

}
