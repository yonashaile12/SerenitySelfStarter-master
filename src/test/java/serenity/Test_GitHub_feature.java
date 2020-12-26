package serenity;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SerenityTest
public class Test_GitHub_feature {

    @DisplayName("This is for GitHub Test1")
    @Test
    @Tag("tag1")
    public void testGitHubGetOneUserEndpointTest() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("username","CybertekSchool")
                .when()
                .get("https://api.github.com/users/{username}").
                then()
                .assertThat()
                .statusCode(200)
        //.log().all();
        ;
        Ensure.that("Test My GitHub Return the data1",
                response -> response.body("login", is("CybertekSchool")));
    }
    @DisplayName("DisplayName This is for GitHub Test2")
    @Test
    public void testManyGitHubGetOneUserEndpoint2() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.github.com/users/CybertekSchool").
                then()
                .assertThat()
                .statusCode(200)
        //.log().all();
        ;
        Ensure.that("Test My GitHub Return the data",
                response -> response.body("login", is("CybertekSchool")));
    }


}
