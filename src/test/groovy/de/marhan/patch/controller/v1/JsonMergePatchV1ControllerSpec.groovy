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
                .body("findAll { it.id == 1}.name", hasItem("test name"))
                .body("findAll", hasSize(1))
                .body("id", hasItems(1))
                .body("name", hasItems("test name"))


    }

    def "create"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given().contentType(ContentType.JSON)
                .body(body)
                .when().post("/v1/persons")
                .then()
                .statusCode(201)
                .body("id", { equalTo(2) })
                .body("name", { equalTo("Jona Meier") })

    }

    def "patch"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given().contentType("application/merge-patch+json")
                .body(body)
                .when().patch("/v1/persons/1")
                .then().statusCode(204)

    }


}