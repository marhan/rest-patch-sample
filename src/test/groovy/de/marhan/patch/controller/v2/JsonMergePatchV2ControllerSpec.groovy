package de.marhan.patch.controller.v2

import de.marhan.patch.controller.SpringBootSpecification
import io.restassured.http.ContentType

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.collection.IsCollectionWithSize.hasSize

class JsonMergePatchV2ControllerSpec extends SpringBootSpecification {

    static final String PATH = "/v2/persons/1"
    static final String PATCH_CONTENT_TYPE = "application/merge-patch+json"

    def "get person"() {

        expect:

        given().contentType(ContentType.URLENC)
                .when().get(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
    }

    def "patch name"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given().contentType(PATCH_CONTENT_TYPE)
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

    def "patch addresses"() {

        given:

        def body = '{"addresses":[{"city": "Hamburg", "street": "Kleine Strasse 1" }]}'

        expect:

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(1))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Kleine Strasse 1"))
    }


}