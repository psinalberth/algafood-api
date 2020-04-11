package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.yml")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository repository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    int totalCozinhas;

    @Before
    public void setUp() throws SQLException {
        RestAssured.basePath = "/cozinhas";
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        databaseCleaner.run();
        inserirDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarContagemCorreta_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("content", hasSize(totalCozinhas));
    }

    private void carregarJson()  {

    }

    private void inserirDados() {

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        repository.save(cozinhaBrasileira);

        Cozinha cozinhaFrancesa = new Cozinha();
        cozinhaFrancesa.setNome("Francesa");
        repository.save(cozinhaFrancesa);

        totalCozinhas = (int) repository.count();
    }
}