package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.core.security.AlgafoodSecurity;
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

    final AlgafoodSecurity algafoodSecurity;

    public RootEntryPointController(AlgafoodSecurity algafoodSecurity) {
        this.algafoodSecurity = algafoodSecurity;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPoint = new RootEntryPointModel();

        if (algafoodSecurity.podeConsultarCidades()) {
            rootEntryPoint.add(linkToCidades("cidades"));
        }

        if (algafoodSecurity.podeConsultarCozinhas()) {
            rootEntryPoint.add(linkToCozinhas("cozinhas"));
        }

        if (algafoodSecurity.podeConsultarEstados()) {
            rootEntryPoint.add(linkToEstados("estados"));
        }

        if (algafoodSecurity.podeConsultarEstatisticas()) {
            rootEntryPoint.add(linkToEstatisticas("estatisticas"));
        }

        if (algafoodSecurity.podeConsultarFormasPagamento()) {
            rootEntryPoint.add(linkToFormasPagamento("formasPagamento"));
        }

        if (algafoodSecurity.podePesquisarPedidos()) {
            rootEntryPoint.add(linkToPedidos("pedidos"));
        }

        if (algafoodSecurity.podeConsultarRestaurantes()) {
            rootEntryPoint.add(linkToRestaurantes("restaurantes"));
        }

        if (algafoodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPoint.add(linkToGrupos("grupos"));
            rootEntryPoint.add(linkToPermissoes("permissoes"));
            rootEntryPoint.add(linkToUsuarios("usuarios"));
        }

        return rootEntryPoint;
    }

    @GetMapping("/swagger")
    public ModelAndView swagger() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }
}
