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

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface Estatisticas {

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }
    }

    @interface GruposUsuariosPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeConsultar { }

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and " +
            "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
            "@algafoodSecurity.getUsuarioId() == #usuarioId)"
        )
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeAlterarUsuario { }

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and " +
            "@algafoodSecurity.getUsuarioId() == #usuarioId"
        )
        @Target(METHOD)
        @Retention(RUNTIME)
        @interface PodeAlterarPropriaSenha { }
    }

    @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro { }

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and " +
            "(hasAuthority('EDITAR_RESTAURANTES') or @algafoodSecurity.gerenciaRestaurante(#restauranteId))"
        )
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
             "hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS') or " +
             "@algafoodSecurity.getUsuarioId() == returnObject.cliente.id or " +
             "@algafoodSecurity.gerenciaRestaurante(returnObject.restaurante.id)"
        )
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar { }

        @PreAuthorize(
             "hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS') or " +
             "@algafoodSecurity.getUsuarioId() == #filtro.clienteId or " +
              "@algafoodSecurity.gerenciaRestaurante(#filtro.restauranteId)"
        )
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeCriar { }

        @PreAuthorize(
             "hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or " +
             "@algafoodSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarPedidos {
        }
    }
}