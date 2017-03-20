package de.marhan.patch.controller.v3

import de.marhan.patch.controller.SpringBootSpecification

class JsonPatchV3ControllerSpec extends SpringBootSpecification {

    public static final String CONTENT_TYPE_VALUE = "application/json-patch+json"

    def "patch replace"() {

        given:

        def body = '[{ "op": "replace", "path": "/name", "value": "my new name" }]'

        expect:

        io.restassured.RestAssured.given().contentType(CONTENT_TYPE_VALUE)
                .body(body)
                .when().patch("/v3/persons/1")
                .then().statusCode(204)

    }


}