package tests;

import static io.restassured.RestAssured.*;

import com.google.common.collect.ImmutableList;
import files.payload;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Collection;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Should pass the method parameters provided by the dataProvider() method")
public class libraryAPITest {

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => aisle={0}, isbn={1}")
    @MethodSource("dataProvider")
    public void addBook(String aisle,String isbn) {
        baseURI="http://216.10.245.166";

        String response =given().

                header("Content-Type","application/json").
                //We will write the addbook method dynamically.
                //What if we have 10 different combinations for this we will use data provider annotations
                body(payload.addBook(aisle,isbn)).
                when().
                post("/Library/Addbook.php").
                then().assertThat().statusCode(200).
                extract().response().asString();

        JsonPath js = new JsonPath(response);

        String id=js.get("ID");

        System.out.println(id);

    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("2928","pqr"),
                Arguments.of("2929","stu")
        );
    }

}
