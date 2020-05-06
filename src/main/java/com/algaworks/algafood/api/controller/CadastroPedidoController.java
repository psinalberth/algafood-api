package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.mapper.PedidoMapper;
import com.algaworks.algafood.api.model.mapper.PedidoResumidoMapper;
import com.algaworks.algafood.api.model.request.PedidoRequest;
import com.algaworks.algafood.api.model.response.PedidoResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoResponse;
import com.algaworks.algafood.api.openapi.controller.CadastroPedidoControllerOpenApi;
import com.algaworks.algafood.core.security.AlgafoodSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class CadastroPedidoController implements CadastroPedidoControllerOpenApi {

    final CadastroPedidoService service;
    final PedidoMapper pedidoMapper;
    final PedidoResumidoMapper pedidoResumidoMapper;
    final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    final AlgafoodSecurity algafoodSecurity;
    final UsuarioService usuarioService;

    public CadastroPedidoController(CadastroPedidoService service, PedidoMapper pedidoMapper,
                                    PedidoResumidoMapper pedidoResumidoMapper,
                                    PagedResourcesAssembler<Pedido> pagedResourcesAssembler,
                                    AlgafoodSecurity algafoodSecurity, UsuarioService usuarioService) {
        this.service = service;
        this.pedidoMapper = pedidoMapper;
        this.pedidoResumidoMapper = pedidoResumidoMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.algafoodSecurity = algafoodSecurity;
        this.usuarioService = usuarioService;
    }

    @Override
    @GetMapping
    public PagedModel<PedidoResumidoResponse> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidosPage = service.listar(pageable);
        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumidoMapper);
    }

    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoResponse buscar(@PathVariable String codigoPedido) {
        Pedido pedido = service.buscarOuFalhar(codigoPedido);
        return pedidoMapper.toModel(pedido);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse salvar(@Valid @RequestBody PedidoRequest request) {
        try {
            Pedido pedido = pedidoMapper.toDomain(request);
            pedido.setCliente(usuarioService.buscarOuFalhar(algafoodSecurity.getUsuarioId()));
            pedido = service.salvar(pedido);
            return pedidoMapper.toModel(pedido);
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}