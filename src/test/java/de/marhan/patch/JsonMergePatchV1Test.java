package de.marhan.patch;

import io.restassured.RestAssured;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.CoreMatchers.equalTo;



@SpringBootTest
@ActiveProfiles(profiles = "test")
public class JsonMergePatchV1Test {

	@Test
	public void contextLoads() {
		RestAssured.baseURI = "http://localhost:10020";
		RestAssured
				.get("/v1/persons")
				.then()
				.statusCode(200).body("name", equalTo("test name"));
	}

}
