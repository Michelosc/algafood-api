package com.algaworks.algafoodapi.api.openapi.controller;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafoodapi.api.model.RestauranteBasicoModel;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.api.model.input.RestauranteInput;
import com.algaworks.algafoodapi.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
            name = "projeção", paramType = "query", type = "string")
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel buscar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
    })
    RestauranteModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
            RestauranteInput restauranteInput
    );

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,

            @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true)
            RestauranteInput restauranteInput
    );

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId
    );

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de um restaurantes", required = true)
                    List<Long> restauranteIds
    );

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso"),
    })
    void inativarMutiplos(
            @ApiParam(name = "corpo", value = "IDs de um restaurantes", required = true)
                    List<Long> restauranteIds
    );

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId
    );

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
                    Long restauranteId
    );

}
