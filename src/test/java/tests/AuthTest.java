package tests;
import io.restassured.path.json.JsonPath;
import org.junit.Test;
import pojo.GetCourse;

import static io.restassured.RestAssured.*;

public class AuthTest {
    @Test
    public void testOAuth(){
        String resp = given()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(resp);
        JsonPath js = new JsonPath(resp);
        String accessToken = js.getString("access_token");

        //Using pojo classes
        GetCourse gc= given()
                .queryParam("access_token", accessToken)
            .when().log().all()
            .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getCourses().getWebAutomation().get(1).getCourseTitle());




    }
}
