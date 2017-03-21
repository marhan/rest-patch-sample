package de.marhan.patch.controller.v1

import de.marhan.patch.controller.SpringBootSpecification
import io.restassured.http.ContentType

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.collection.IsCollectionWithSize.hasSize

class JsonMergePatchV1ControllerSpec extends SpringBootSpecification {

    def "get persons"() {

        expect:

        given().contentType(ContentType.URLENC)
                .when().get("/v1/persons")
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("findAll { it.id == 1}.name", hasItem("Fritz Brause"))
                .body("findAll", hasSize(1))
                .body("id", hasItems(1))
                .body("name", hasItems("Fritz Brause"))


    }

    def "patch name"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given().contentType("application/merge-patch+json")
                .body(body)
                .when().patch("/v1/persons/1")
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("name", equalTo("Jona Meier"))
                .body("id", equalTo(1))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))

    }

    def "patch addresses"() {

        given:

        def body = '{"addresses":[{"city": "Hamburg", "street": "Kleine Strasse 1" }]}'

        expect:

        given().contentType("application/merge-patch+json")
                .body(body)
                .when().patch("/v1/persons/1")
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("name", equalTo("Fritz Brause"))
                .body("id", equalTo(1))
                .body("addresses.findAll", hasSize(1))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Kleine Strasse 1"))
    }


}