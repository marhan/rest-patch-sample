package de.marhan.patch.patch

import de.marhan.patch.resource.PersonResource
import groovy.json.JsonOutput
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.runner.RunWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.equalTo

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
class JsonMergePatchSpec extends Specification {


    def commercialData = []


    def setup() {
        RestAssured.baseURI = "http://localhost:10020"

    }

    def "read 1"() {

        expect:
        RestTemplate restTemplate = new RestTemplate()

        List<PersonResource> products =
                restTemplate.getForObject("http://localhost:10020/v1/persons", PersonResource.class)
    }

    def "read"() {

        expect:

        RestAssured
                .get("/v1/persons")
                .then()
                .statusCode(200).body("name", { equalTo("test") })
    }

    def "create"() {

        expect:

        given().contentType(ContentType.JSON).body(JsonOutput.toJson(commercialData))
                .when()
                .post("/v1/persons")
                .then()
                .statusCode(201)
                .body("services", { equalTo("my services") })
    }


    def "test"() {

        when:

        def test = ""

        then:

        test == ""

    }


}