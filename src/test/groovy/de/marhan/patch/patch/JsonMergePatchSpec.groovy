package de.marhan.patch.patch

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import spock.lang.Shared

class JsonMergePatchSpec extends SpringBootSpecification {

    @Shared
    RESTClient client = new RESTClient("http://localhost:12345")

    JsonSlurper jsonSlurper = new JsonSlurper()

    def "get persons"() {

        when:

        def response = client.get(path: '/v1/persons')

        then:

        response != null
        with(response) {
            status == 200
            data == jsonSlurper.parseText('[{ "id": 1, "name": "test name" }]')
        }


    }

    def "create"() {

        when:

        def response = client.post(
                path: '/v1/persons',
                body: [name: "Jona Meier"],
                requestContentType: ContentType.JSON)

        then:

        response != null
        with(response) {
            status == 201
            data == jsonSlurper.parseText('{ "id": 2, "name": "Jona Meier" }')
        }
    }

    def "patch"() {

        when:

        def response = client.patch(
                path: '/v1/persons/1',
                body: [name: "Jona Meier"],
                requestContentType: ContentType.JSON)

        then:

        response != null
        with(response) {
            status == 201
        }

    }


}