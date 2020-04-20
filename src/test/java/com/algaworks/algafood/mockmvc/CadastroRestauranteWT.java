package com.algaworks.algafood.mockmvc;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.mapper.RestauranteMapper;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.util.JsonStringLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RestauranteController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CadastroRestauranteWT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RestauranteService service;

    @MockBean
    private RestauranteMapper mapper;

    @Test
    public void deveRetornarStatus405_QuandoExcluirRestaurante() throws Exception {
        mockMvc.perform(delete(URI.create("/restaurantes/1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deveRetornarStatus204_QuandoInativarRestaurante() throws Exception {
        mockMvc.perform(delete(URI.create("/restaurantes/2/ativo"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deveRetornarStatus201_QuandoEnviarRestaurante() throws Exception {
        String restauranteCompleto = JsonStringLoader
                .loadContent("/input/success/restaurante.json");
        mockMvc.perform(post(URI.create("/restaurantes"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(restauranteCompleto)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void deveRetornarStatus404_QuandoBuscarRestauranteInexistente() throws Exception {

        Mockito.when(service.buscarOuFalhar(2L))
                .thenThrow(new RestauranteNaoEncontradoException(2L));

         ResultActions resultActions = mockMvc.perform(get(URI.create("/restaurantes/2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        assertEquals(RestauranteNaoEncontradoException.class,
                Objects.requireNonNull(resultActions.andReturn().getResolvedException()).getClass());
    }
}