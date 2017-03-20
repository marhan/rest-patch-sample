package de.marhan.patch.controller.v2

import de.marhan.patch.controller.SpringBootSpecification

import static io.restassured.RestAssured.given

class JsonMergePatchV2ControllerSpec extends SpringBootSpecification {

    def "patch"() {

        given:

        def body = '{"name": "Jona Meier" }'

        expect:

        given().contentType("application/merge-patch+json")
                .body(body)
                .when().patch("/v2/persons/1")
                .then().statusCode(204)

    }


}