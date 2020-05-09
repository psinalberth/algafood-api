package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
public @interface SecurityConstants {

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("@algafoodSecurity.podeConsultarCidades()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("@algafoodSecurity.podeConsultarCozinhas()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("@algafoodSecurity.podeConsultarEstados()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Estatisticas {

        @PreAuthorize("@algafoodSecurity.podeConsultarEstatisticas()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("@algafoodSecurity.podeConsultarFormasPagamento()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface GruposUsuariosPermissoes {

        @PreAuthorize("@algafoodSecurity.podeEditarUsuariosGruposPermissoes()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("@algafoodSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and " +
            "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
            "@algafoodSecurity.usuarioAutenticadoIgual(#usuarioId))"
        )
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeAlterarUsuario { }

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and " +
            "@algafoodSecurity.usuarioAutenticadoIgual(#usuarioId)"
        )
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeAlterarPropriaSenha { }
    }

    @interface Restaurantes {

        @PreAuthorize("@algafoodSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro { }

        @PreAuthorize("@algafoodSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("@algafoodSecurity.podeConsultarRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
            "hasAuthority('CONSULTAR_PEDIDOS') or " +
            "@algafoodSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or " +
            "@algafoodSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar { }

        @PreAuthorize("@algafoodSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeCriar { }

        @PreAuthorize("@algafoodSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarPedidos {
        }
    }
}