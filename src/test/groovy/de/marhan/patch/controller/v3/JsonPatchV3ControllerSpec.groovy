package de.marhan.patch.controller.v3

import de.marhan.patch.controller.SpringBootSpecification
import io.restassured.http.ContentType

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.collection.IsCollectionWithSize.hasSize

class JsonPatchV3ControllerSpec extends SpringBootSpecification {


    static final String PATH = "/v3/persons/1"
    static final String PATCH_CONTENT_TYPE = "application/json-patch+json"

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

    def "patch replace name"() {

        given:

        def body = '[{ "op": "replace", "path": "/name", "value": "Jona Meier" }]'

        expect:

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v3/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Jona Meier"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))

    }


    def "patch remove address"() {

        given:

        def body = '[{ "op": "remove", "path": "/addresses/1"}]'

        expect:

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v3/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(1))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))

    }

    def "patch replace address"() {

        given:

        def body = '[{ "op": "replace", "path": "/addresses", "value": [{"city": "Hannover", "street": "Hauptstrasse 100"}, {"city": "Bremen", "street": "Bahnhofstrasse 99"}] }]'

        expect:

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v3/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Bahnhofstrasse 99"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("Hauptstrasse 100"))

    }


}