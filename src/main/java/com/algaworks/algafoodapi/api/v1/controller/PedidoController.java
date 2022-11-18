package com.algaworks.algafoodapi.api.v1.controller;

import com.algaworks.algafoodapi.api.v1.assembler.PedidoInputDisassembler;
import com.algaworks.algafoodapi.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafoodapi.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafoodapi.api.v1.model.PedidoModel;
import com.algaworks.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.v1.model.input.PedidoInput;
import com.algaworks.algafoodapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafoodapi.core.data.PageWrapper;
import com.algaworks.algafoodapi.core.data.PageableTranslator;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Pedido;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import com.algaworks.algafoodapi.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping(path ="/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;


    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<PedidoResumoModel> todosPedidos =
//                pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(todosPedidos);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter",
//                    SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(@PageableDefault(size = 10) PedidoFilter filtro,
                                                   Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);
            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal","valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }

}
