package ZipToCity;

import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.is;

@SerenityTest
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
    @ValueSource(strings = {"22030", "22031", "22032", "22034", "22035"})
    public void testZipCode(String zip){

        // run this parameterized test with 5 zipcodes of your choice
        // start with no external file
        // then add external csv file in separate test
        System.out.println("zip = " + zip);
        given()
                .pathParam("country", "us")
                .pathParam("zipcode", zip).
                when()
                .get("/{country}/{zipcode}");

        Ensure.that("We got successful result", v -> v.statusCode(200));
    }

    /**
     * {index} -->> to represent iteration number
     * @param zip
     *{arguments} --->>
     * {methodParameterIndexNumber} --->>
     */

//    @DisplayName("Testing multiple zip code and get the result")
    @ParameterizedTest(name = "Iteration number {index} : {arguments} ")
    @ValueSource(strings = {"22030", "22031", "22032", "22034", "22035"})
    public void testDisplayNameManipulation(String zip){

    }

    @ParameterizedTest(name="Iteration number {index} Country is {0} , Zipcode is {1}")
    @CsvFileSource(resources = "features/country_zip.csv", numLinesToSkip = 1)
    public void testCountryZip(String country, int zip){
        given()
                .pathParam("country",country)
                .pathParam("zipcode",zip).
                when()
                .get("/{country}/{zipcode}") ;
        Ensure.that("we got successful result ", v -> v.statusCode(200) ) ;
    }
}
