package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.controller.PedidoController;
import com.algaworks.algafoodapi.api.controller.RestauranteController;
import com.algaworks.algafoodapi.api.controller.UsuarioController;
import com.algaworks.algafoodapi.api.model.PedidoModel;
import com.algaworks.algafoodapi.api.model.PedidoResumoModel;
import com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

        pedidoModel.getRestaurante()
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(RestauranteController.class)
                        .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModel.getCliente()
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioController.class)
                        .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoModel;
    }
}
