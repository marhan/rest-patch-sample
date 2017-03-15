package de.marhan.patch.patch

import groovy.json.JsonOutput
import io.restassured.RestAssured
import io.restassured.http.ContentType

class JsonMergePatchSpec extends SpringBootSpecification {


    def person = ["id": 1, "name": "test"]


    def setup() {

    }


    def "get persons"() {

        expect:

        RestAssured.get("/v1/persons")
                .then()
                .statusCode(200)
    }

    def "create"() {

        expect:

        RestAssured.given().contentType(ContentType.JSON).body(JsonOutput.toJson(person))
                .when()
                .post("/v1/persons")
                .then()
                .statusCode(201)
    }


}