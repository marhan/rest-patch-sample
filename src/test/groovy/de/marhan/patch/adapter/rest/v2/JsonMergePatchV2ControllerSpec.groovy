package de.marhan.patch.adapter.rest.v2

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class JsonMergePatchV2ControllerSpec extends Specification {

    private static final String PATH = "/v2/persons/1"
    private static final String PATCH_CONTENT_TYPE = "application/merge-patch+json"

    @LocalServerPort
    private long port

    void "get person"() {

        expect:

        given()
                .contentType(ContentType.URLENC)
                .when().get(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
    }

    void "patch name"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given()
                .contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Jona Meier"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
    }

    void "patch addresses"() {

        given:

        def body = '{"addresses":[{"city": "Hamburg", "street": "Kleine Strasse 1" }]}'

        expect:

        given()
                .contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(1))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Kleine Strasse 1"))
    }

    private given() {
        RestAssured.given().baseUri("http://localhost:" + port)
    }

}
