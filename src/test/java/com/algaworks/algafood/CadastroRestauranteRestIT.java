package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteRestIT {

    private static final int RESTAURANTE_ID_INEXISTENTE = 1000;

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private int restaurantesSize;

    private Restaurante restauranteMineiro;

    private String jsonCadastroRestaurante;

    private String jsonRestauranteSemFrete;

    private String jsonRestauranteSemCozinha;

    private String jsonRestauranteComCozinhaInexistente;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();

        prepararDados();
        jsonCadastroRestaurante = ResourceUtils.getContentFromResource("/json/cadastro-restaurante.json");
        jsonRestauranteSemCozinha =
                ResourceUtils.getContentFromResource("/json/restaurante-sem-cozinha.json");
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Mineira");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Nordestina");
        cozinhaRepository.save(cozinha2);


        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Restaurante Mineiro");
        restaurante1.setTaxaFrete(BigDecimal.TEN);
        restaurante1.setCozinha(cozinha1);
        restauranteRepository.save(restaurante1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Restaurante Nordestino");
        restaurante2.setTaxaFrete(BigDecimal.TEN);
        restaurante2.setCozinha(cozinha2);

        restauranteRepository.save(restaurante2);

        restauranteMineiro = restaurante1;

        restaurantesSize = (int) restauranteRepository.count();
    }

    @Test
    public void deveRetornar1Restaurante_QuandoConsultarRestaurantesComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(restaurantesSize));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestauranteComSucesso() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(jsonCadastroRestaurante)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteMineiro.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(restauranteMineiro.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
                .body(jsonRestauranteSemCozinha)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

}
