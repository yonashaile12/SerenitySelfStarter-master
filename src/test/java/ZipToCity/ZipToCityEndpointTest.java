package ZipToCity;

import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runners.Parameterized;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;

public class ZipToCityEndpointTest {
        //https://zippopotam.us/{country}/{Zipcode}
    /*
    "post code": "60640",
    "country": "United States",
    "country abbreviation": "US",
    "places": [
        {
            "place name": "Chicago",
            "longitude": "-87.6624",
            "state": "Illinois",
            "state abbreviation": "IL",
            "latitude": "41.9719"
     */
    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://zippopotam.us";
    }
    @AfterAll
    public static void cleanUp(){
       clear();
    }

    @DisplayName("Testing 1 zip code and get the result")
    @Test
    public void test1ZipCode(){
        given()
                .pathParam("country", "us")
                .pathParam("zipcode", "60640").
        when()
                .get("/{country}/{zipcode}").
        then()
                .statusCode(200)
                .body("'post code'", is("60640"))
                .body("places[0].'place name'", is("Chicago"))
        ;
    }


    @DisplayName("Testing multiple zip code and get the result")
    @ParameterizedTest
    public void testZipCode(){

        // run this parameterized test with 5 zipcodes of your choice
        // start with no external file
        // then add external csv file in separate test

    }
}
