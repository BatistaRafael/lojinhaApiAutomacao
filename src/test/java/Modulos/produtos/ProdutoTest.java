package Modulos.produtos;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;





@DisplayName("Testes API rest no modulo de produto")
public class ProdutoTest {
    @Test
    @DisplayName("Validar os limites proibidos do valor do produto")
    public void testValidarLimitesProibidosValorProduto(){

        // Configurando os dados da API rest da lojinha
        baseURI = "http://165.227.93.41";
       // port = 8080;
        basePath = "/lojinha";


        // Obter o token do usuario admin
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"usuarioLogin\": \"admin\",\n" +
                        "  \"usuarioSenha\": \"admin\"\n" +
                        "}")
            .when()
                .post("/v2/login")
            .then()
                .extract()
                .path("data.token");


        System.out.println(token);



        // tentar inserir o produto com valor 0.00 e validar que a mensagem de erro foi apresentada e o

        given()
                .contentType(ContentType.JSON)
                .header("token", token)
                .body("{\n" +
                        "  \"produtoNome\": \"playstation 5\",\n" +
                        "  \"produtoValor\": 0.00,\n" +
                        "  \"produtoCores\": [\n" +
                        "    \"preto\"\n" +
                        "  ],\n" +
                        "  \"produtoUrlMock\": \"\",\n" +
                        "  \"componentes\": [\n" +
                        "    {\n" +
                        "      \"componenteNome\": \"controle\",\n" +
                        "      \"componenteQuantidade\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
            .when()
                .post("/v2/produtos")
            .then()
                    .assertThat()
                            .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                            .statusCode(422);


        // status code retornado foi 422

    }

}
