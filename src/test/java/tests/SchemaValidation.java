package tests;

import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class SchemaValidation {
    @Test
    public void testGet(){
        baseURI = "https://reqres.in/api";
        given().
            get("users?page=2").
        then().
            assertThat().body(matchesJsonSchemaInClasspath("schema.json"));

    }
}
