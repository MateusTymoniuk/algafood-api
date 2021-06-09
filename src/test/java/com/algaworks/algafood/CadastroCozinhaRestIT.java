package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.utils.DatabaseCleaner;
import com.algaworks.algafood.utils.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaRestIT {

    public static final int ID_DE_COZINHA_INEXISTENTE = 1000;
    private int cozinhasSize;

    private Cozinha cozinhaNordestina;

    private String jsonCadastroCozinha;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();

        prepararDados();
        jsonCadastroCozinha = ResourceUtils.getContentFromResource("/json/cadastro-cozinha.json");
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Mineira");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Nordestina");
        cozinhaRepository.save(cozinha2);

        cozinhasSize = (int) cozinhaRepository.count();

        cozinhaNordestina = cozinha2;
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhasComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornar2Cozinhas_QuandoConsultarCozinhasComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(cozinhasSize));
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
            .accept(ContentType.JSON)
            .pathParam("cozinhaId", cozinhaNordestina.getId())
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaNordestina.getNome()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarNovaCozinhaComSucesso() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(jsonCadastroCozinha)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .accept(ContentType.JSON)
            .pathParam("cozinhaId", ID_DE_COZINHA_INEXISTENTE)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
