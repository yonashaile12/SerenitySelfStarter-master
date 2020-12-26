package serenity.spartan;

import Utility.SpartanUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SerenityTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class SimpleSpartanTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.224.154.167:8000";
        RestAssured.basePath = "/api";
        //System.out.println("getDefaultRequestSpecification() = "+getDefaultRequestSpecification());
    }

    @AfterAll
    public static void cleanUp(){
        RestAssured.reset();
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





}