package de.marhan.patch.patch

import groovy.json.JsonOutput
import io.restassured.http.ContentType

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.collection.IsCollectionWithSize.hasSize

class JsonMergePatchSpec extends SpringBootSpecification {

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

        expect:

        given().contentType(ContentType.JSON)
                .body(JsonOutput.toJson([name: "Jona Meier"]))
                .when().post("/v1/persons")
                .then()
                .statusCode(201)
                .body("id", { equalTo(2) })
                .body("name", { equalTo("Jona Meier") })

    }

    def "patch"() {

        expect:

        given().contentType("application/merge-patch+json")
                .body(JsonOutput.toJson([name: "Jona Meier"]))
                .when().patch("/v1/persons/1")
                .then().statusCode(204)

    }


}