package tests;

import files.payload;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GoogleAPI {

    String placeId="";

    @Test
    public void test_1() throws IOException {
        //given - inputs of the API
        //when - the API call is made - http method, resources the rest of the API end point.
        //then - validate the API

        baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().
                            queryParam("key","qaclick123").
                            header("Content-Type","application/json").
                //Body comes from a file
                            body(new String(Files.readAllBytes(Paths.get("C:\\Users\\mkhapali\\IdeaProjects\\RestAssured\\src\\test\\java\\files\\static.json")))).
                        when().post("maps/api/place/add/json?key=qaclick123").
                        then().assertThat().statusCode(200)
                            //Body level assertions
                            .body("scope",equalTo("APP"))
                            .header("server","Apache/2.4.52 (Ubuntu)")
                            .extract().response().asString();
    //Add Place -> update place with new address -> Get the place to validate if the new address is present.

        System.out.println(response);
        //For Parsing Json using RestAssured.
        JsonPath js = new JsonPath(response);
         placeId = js.getString("place_id");

        System.out.println(placeId);

        //Test for Put
        given().log().all().
                queryParam("key","qaclick123").
                header("Content-Type","application/json").
                body(payload.UpdatePlace(placeId)).
        when().put("maps/api/place/update/json").
        then().log().all().assertThat().statusCode(200).
                body("msg",equalTo("Address successfully updated"));

        //Verify the update using Get

        given().log().all().
                queryParam("key","qaclick123").
                queryParam("place_id",placeId).
        when().get("maps/api/place/get/json").
        then().log().all().assertThat().statusCode(200).
                body("address",equalTo("70 winter walk, USA"));
    }



}
