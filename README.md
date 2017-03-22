# RESTful service sample with HTTP PATCH

The intention of this project is to experiment with several HTTP PATCH method implementations. 
None of the implemented variants are meant to be best or good practices. 

## JSON MERGE PATCH

Please find examples for [RFC7386 (JSONMerge Patch)](http://tools.ietf.org/html/rfc7386) in groovy test classes JsonMergePatchV1ControllerSpec and JsonMergePatchV2ControllerSpec.

## JSON PATCH

Please find example for [RFC 6902 (JSON Patch)](http://tools.ietf.org/html/rfc6902) in groovy test class JsonPatchV3ControllerSpec.

## Run tests

```
./gradlew clean test -Dspring.profiles.active=test
```

## Continuous Integration

- [Travis CI](https://travis-ci.org/marhan/rest-patch-sample)
- On your Jenkins with the given [Jenkinsfile](rest-patch-sample/Jenkinsfile) 


## Used components / technologies

- [Spring Boot](https://projects.spring.io/spring-boot/) 
- [Rest-assured](https://github.com/rest-assured/rest-assured/wiki/GettingStarted)
- [JSON-Patch](https://github.com/daveclayton/json-patch)



