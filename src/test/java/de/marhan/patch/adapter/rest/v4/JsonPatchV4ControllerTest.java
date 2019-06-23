package de.marhan.patch.adapter.rest.v4;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class JsonPatchV4ControllerTest {

    private static final String PATH = "/v4/persons/1";
    private static final String PATCH_CONTENT_TYPE = "application/json-patch+json";

    @LocalServerPort
    private long port;

    @Test
    void getPerson() {

        given().contentType(ContentType.URLENC)
                .when().get(PATH)
                .then().contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"));
    }

    @Test
    void patchReplaceName() {

        String body = "[{ \"op\": \"replace\", \"path\": \"/name\", \"value\": \"Jona Meier\" }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Jona Meier"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"));
    }

    @Test
    void patchTestNameSuccessful() {

        String body = "[{ \"op\": \"test\", \"path\": \"/name\", \"value\": \"Fritz Brause\" }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"));
    }

    @Test
    void patchTestNameWithFailure() {

        String body = "[{ \"op\": \"test\", \"path\": \"/name\", \"value\": \"Totally wrong value\" }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(404)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"));

    }

    @Test
    void patchRemoveAddress() {

        String body = "[{ \"op\": \"remove\", \"path\": \"/addresses/1\"}]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(1))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"));

    }

    @Test
    void patchReplaceAddresses() {

        String body = "[{ \"op\": \"replace\", \"path\": \"/addresses\", \"value\": [{\"city\": \"Hannover\", \"street\": \"Hauptstrasse 100\"}, {\"city\": \"Bremen\", \"street\": \"Bahnhofstrasse 99\"}] }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Bahnhofstrasse 99"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("Hauptstrasse 100"));
    }

    @Test
    void patchReplaceOneSpecificAddress() {

        String body = "[{ \"op\": \"replace\", \"path\": \"/addresses/0\", \"value\": {\"city\": \"Hannover\", \"street\": \"The replaced street 1\"} }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("The replaced street 1"));

    }


    @Disabled("Does not work as expected!")
    @Test
    void PatchReplaceAddressesStreet() {

        String body = "[{ \"op\": \"replace\", \"path\": \"/addresses/0/street\", \"value\": \"The replaced street 1\" }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(2))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("The replaced street 1"));
    }

    @Test
    void patchAddAddress() {

        String body = "[{ \"op\": \"add\", \"path\": \"/addresses/0\", \"value\": {\"city\": \"Hannover\", \"street\": \"Hauptstrasse 100\"} }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(3))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("Hauptstrasse 100"));

    }

    @Test
    void patchAddAddressAtTheEnd() {

        String body = "[{ \"op\": \"add\", \"path\": \"/addresses/-\", \"value\": {\"city\": \"Hannover\", \"street\": \"Hauptstrasse 100\"} }]";

        given().contentType(PATCH_CONTENT_TYPE)
                .body(body)
                .when().patch("/v4/persons/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Fritz Brause"))
                .body("addresses.findAll", hasSize(3))
                .body("addresses.findAll { it.city == 'Hamburg'}.street", hasItem("Spitalerstrasse 12"))
                .body("addresses.findAll { it.city == 'Bremen'}.street", hasItem("Boetcherstrasse 2"))
                .body("addresses.findAll { it.city == 'Hannover'}.street", hasItem("Hauptstrasse 100"));

    }

    private RequestSpecification given() {
        return RestAssured.given().baseUri("http://localhost:" + port);
    }
}
