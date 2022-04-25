package Modulos.produtos;


import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;





@DisplayName("Testes API rest no modulo de produto")
public class ProdutoTestRefatorado {

    private String token;

    @BeforeEach
    public void beforeEach(){
        // Configurando os dados da API rest da lojinha
        baseURI = "http://165.227.93.41";
        // port = 8080;
        basePath = "/lojinha";



        // Obter o token do usuario admin
        this.token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
            .when()
                .post("/v2/login")
            .then()
                .extract()
                    .path("data.token");

        System.out.println(token);



        // tentar inserir o produto com valor 0.00 e validar que a mensagem de erro foi apresentada e o
        // status code retornado foi 422

    }
    @Test
    @DisplayName("Validar os que o valor do produto igual a 7000.01 não é permitido")
    public void testValidarLimitesMaioreSeteMilProibidoValorProduto(){

        // tentar inserir o produto com valor 0.00 e validar que a mensagem de erro foi apresentada e o
        // status code retornado foi 422


        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
            .when()
                .post("/v2/produtos")
            .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);



    }
    @Test
    @DisplayName("Validar os que o valor do produto igual a 0.00 não é permitido")
    public void testValidarLimitesZeradoProibidosValorProduto(){

        // tentar inserir o produto com valor 0.00 e validar que a mensagem de erro foi apresentada e o
        // status code retornado foi 422


        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
            .when()
                .post("/v2/produtos")
            .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);



    }
}
