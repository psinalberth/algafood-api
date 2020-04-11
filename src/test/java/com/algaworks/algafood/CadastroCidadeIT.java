package com.algaworks.algafood;

import com.algaworks.algafood.util.JsonStringLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
public class CadastroCidadeIT {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        basePath = "/cidades";
        RestAssured.port = port;
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCidades() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCidadeInexistente() {
        given()
                .accept(ContentType.JSON)
                .pathParam("cidadeId", 500)
        .when()
                .get("/{cidadeId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoEnviarCidadeComEstadoInexistente() {
        String cidadeComEstadoInexistente = JsonStringLoader
                .loadContent("/input/fail/cidade-com-estado-inexistente.json");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(cidadeComEstadoInexistente)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoEnviarCidadeSemEstado() {
        String cidadeSemEstado = JsonStringLoader
                .loadContent("/input/fail/cidade-sem-estado.json");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(cidadeSemEstado)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}