package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.AlgaLinks;
import com.algaworks.algafoodapi.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.GrupoModel;
import com.algaworks.algafoodapi.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        return grupoModelAssembler.toCollectionModel(usuario.getGrupos()).removeLinks();
    }

    @DeleteMapping(path ="/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
