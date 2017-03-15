package de.marhan.patch.patch

import de.marhan.patch.RestPatchSampleApplication
import groovy.json.JsonOutput
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class JsonMergePatchSpec extends Specification {


    def person = ["id": 1, "name": "test"]


    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    void setupSpec() {
        Future future = Executors
                .newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    ConfigurableApplicationContext call() throws Exception {
                        return (ConfigurableApplicationContext) SpringApplication
                                .run(RestPatchSampleApplication.class)
                    }
                })
        context = future.get(60, TimeUnit.SECONDS)
    }

    def setup() {
        RestAssured.baseURI = "http://localhost:12345"

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