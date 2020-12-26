package serenity.spartan;

import Utility.SpartanUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

@SerenityTest
public class SimpleSpartanTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.224.154.167:8000";
        RestAssured.basePath = "/api";
    }

    @AfterAll
    public static void cleanUp(){
        reset();
    }

    @DisplayName("Testing GET /api/hello Endpoint")
    @Test
    public void testingHelloEndPoint(){

        when()
                .get("/hello").
                then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body( is("Hello from Sparta") )
        ;

        // Serenity's of way of generating some steps for verification
        // in the report using Ensure class
        Ensure.that("Make sure endpoint works ",
                    response -> response
                                .statusCode(200)
                                .contentType(ContentType.TEXT)
                                .body( is("Hello from Sparta") )
                   );

        Ensure.that("Success Response was received",
                     thenResponse -> thenResponse.statusCode(200) )
                .andThat("I got text response",
                        blaResponse -> blaResponse.contentType(ContentType.TEXT))
                .andThat("I got Hello from Sparta",
                        vResponse ->vResponse.body(is("Hello from Sparta"))
                )
                .andThat("i got my response time within 2 seconds",
                        vResponse->vResponse.time(lessThan(2L), TimeUnit.SECONDS))
        ;

    }

    @DisplayName("Admin user should be able to Add Spartan")
    @Test
    public void testAdd1Data(){

        Map<String, Object> payload = SpartanUtil.getRandomSpartanRequestPayLoad();

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payload).
        when()
                .post("/spartans") ;

        Ensure.that("Request was successful",
                thenResponse->thenResponse.statusCode(201))
               .andThat("We got json format result",
                       thenResponse->thenResponse.contentType(ContentType.JSON))
               .andThat("Success Message is A Spartan is Born!",
                       thenResponse->thenResponse.body("success", is("A Spartan is Born!")))
        ;






    }




}