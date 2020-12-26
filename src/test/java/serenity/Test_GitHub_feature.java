package serenity;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.is;

@SerenityTest
public class Test_GitHub_feature {

    @DisplayName("Initial DEMO for GitHub User test")
    @Test
//    @Tag("tag1")
    public void testGitHubGetOneUserEndpointTest() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("username","CybertekSchool").
                when()
                .get("https://api.github.com/users/{username}").
                then()
                // this is regular RestAssured way
                .assertThat()
                .statusCode(200)
                .body("login", is("CybertekSchool")) ;
        // this is regular RestAssured way
    }
    @DisplayName("This is serenity way of providing test result")
    @Test
    public void testManyGitHubGetOneUserEndpoint2() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("username","CybertekSchool").
                when()
                .get("https://api.github.com/users/{username}");
        // this is serenity way we can generate custom report for validation steps
        Ensure.that("Response was successful (instead of saying 200 : )",
                response -> response.statusCode( 200  )
        ) ;
        Ensure.that("This is custom message for verifying login field in response",
                response -> response.body("login", is("CybertekSchool")));
    }



}
