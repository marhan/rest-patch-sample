# RESTful service sample with HTTP PATCH

The intention of this project is to experiment with several HTTP PATCH method implementations. 
None of the implemented variants are meant to be best or good practices. 

## JSON MERGE PATCH

Have a look at the tests JsonMergePatchV1ControllerSpec and JsonMergePatchV2ControllerSpec.

## JSON PATCH

Have a look at the test JsonPatchV3ControllerSpec.

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



