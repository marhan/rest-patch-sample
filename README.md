# RESTful service sample with HTTP PATCH

The intention of this project is to experiment with [RFC7386 (JSONMerge Patch)](http://tools.ietf.org/html/rfc7386) and [RFC 6902 (JSON Patch)](http://tools.ietf.org/html/rfc6902) implementations. 
None of the implemented variants are meant to be best or good practices. 

## Run tests

```
./gradlew clean test -Dspring.profiles.active=test
```

## Run application

```
./gradlew bootRun
```

## Use application

Please find some HTTP PATCH example calls for the three different endpoints. 
[HTTPie](https://httpie.org/doc) is used to call the API. 
Therefore, you need to install it, if you want to use the commands below directly in your terminal.

There is also a [Postman](https://www.getpostman.com/) collection in /doc folder.

### JSON MERGE PATCH - V1

```
 http PATCH :10000/v1/persons/1 name="Test Name" Content-Type:application/merge-patch+json
```

### JSON MERGE PATCH - V2

```
 http PATCH :10000/v2/persons/1 name="Test Name" Content-Type:application/merge-patch+json
```

### JSON PATCH - V3 

```
 echo '[{ "op": "replace", "path": "/name", "value": "Test Name" }]' | http PATCH :10000/v3/persons/1 Content-Type:application/json-patch+json
```

## Continuous Integration

- [Travis CI](https://travis-ci.org/marhan/rest-patch-sample)
- On your Jenkins with the given [Jenkinsfile](rest-patch-sample/Jenkinsfile) 


## Used components / technologies

- [Spring Boot](https://projects.spring.io/spring-boot/) 
- [Rest-assured](https://github.com/rest-assured/rest-assured/wiki/GettingStarted)
- [JSON-Patch](https://github.com/daveclayton/json-patch)



