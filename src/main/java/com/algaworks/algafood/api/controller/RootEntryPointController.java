package com.algaworks.algafood.api.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import static com.algaworks.algafood.api.AlgaLinks.*;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPoint = new RootEntryPointModel();

        rootEntryPoint.add(linkToCidades("cidades"));
        rootEntryPoint.add(linkToCozinhas("cozinhas"));
        rootEntryPoint.add(linkToEstados("estados"));
        rootEntryPoint.add(linkToEstatisticas("estatisticas"));
        rootEntryPoint.add(linkToFormasPagamento("formasPagamento"));
        rootEntryPoint.add(linkToGrupos("grupos"));
        rootEntryPoint.add(linkToPedidos("pedidos"));
        rootEntryPoint.add(linkToPermissoes("permissoes"));
        rootEntryPoint.add(linkToRestaurantes("restaurantes"));
        rootEntryPoint.add(linkToUsuarios("usuarios"));

        return rootEntryPoint;
    }

    @GetMapping("/swagger")
    public ModelAndView swagger() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
