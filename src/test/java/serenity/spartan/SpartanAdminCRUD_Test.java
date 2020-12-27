package serenity.spartan;

import Utility.SpartanUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

@SerenityTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class SpartanAdminCRUD_Test {
    public Map<String,Object> putPayload = SpartanUtil.getRandomSpartanRequestPayLoad();
    public Map<String,Object> postPayload = SpartanUtil.getRandomSpartanRequestPayLoad();
    public Map<String,String> patchPayload = new HashMap<>();
    //static RequestSpecification adminSpec;
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://54.224.154.167:8000";
        RestAssured.basePath = "/api";
        // this is setting static field of rest assured class requestSpecification
        // to the value we specified
        // this is global variable which every method can use
         //adminSpec = given().auth().basic("admin","admin");
    }
    @AfterAll
    public static void cleanUp(){
        reset();
    }
    @DisplayName("1. Admin User Should be able to Add Spartan")
    @Test
    public void testAdd1Data(){

        given()
                .log().body()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(postPayload).
        when()
                .post("/spartans") ;
        Ensure.that("Request was successful"  ,
                thenResponse -> thenResponse.statusCode(201) )
                .andThat("We got json format result",
                        thenResponse -> thenResponse.contentType(ContentType.JSON) )
                .andThat("success message is A Spartan is born!" ,
                        thenResponse -> thenResponse.body("success",is("A Spartan is Born!") )  )
        ;
        Ensure.that("The data " + postPayload + " we provided added correctly",
                vRes -> vRes.body("data.name", is( postPayload.get("name")  ) )
                        .body("data.gender", is( postPayload.get("gender")  ) )
                        .body("data.phone", is( postPayload.get("phone")  ) ) )
                .andThat("New ID has been generated and not null" ,
                        vRes -> vRes.body("data.id" , is(not(nullValue() )))    ) ;
        // how do we extract information after sending requests ? :
        // for example I want to print out ID
        // lastResponse() method is coming SerenityRest class
        // and return the Response Object obtained from last ran request.
//        lastResponse().prettyPeek();
        System.out.println("lastResponse().jsonPath().getInt(\"data.id\") = "
                + lastResponse().jsonPath().getInt("data.id"));
    }
    @DisplayName("2. Admin Should be able to read single data")
    @Test
    public void getOneData(){

        int newID = lastResponse().jsonPath().getInt("data.id") ;
//        System.out.println("newID = " + newID);
        given()
                .auth().basic("admin","admin").
        when()
                .get("/spartans/{id}", newID) ;

        Ensure.that("We can access newly generated data",
                thenResponse-> thenResponse.statusCode(200) ) ;
        // add all validation for body here as homework
        // add put and patch as homework

//        Ensure.that("We can get all the Json generated Data",
//                thenResponse ->thenResponse.body("name", is(payload.get("name") ))
                //.body("gender", is(payload.get("gender")))
                    //);

    }

    @DisplayName("3. Admin Should be able to update a single data")
    @Test
    public void updateOneData(){
        int newID = lastResponse().jsonPath().getInt("id");

        given()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(putPayload).
        when()
                .put("/spartans/{id}", newID);
        Ensure.that("The data provided added correctly",
                theResponse->theResponse.statusCode(204));
        given()
                .auth().basic("admin","admin").
                when()
                .get("/spartans/{id}", newID) ;

        Ensure.that("We can access newly generated data",
                thenResponse-> thenResponse.statusCode(200) ) ;

        given()
                .auth().basic("admin","admin").
        when()
                .get("/spartans/{id}", newID) ;

    }

    @DisplayName("4. Admin Should be able to partailly update a single data")
    @Test
    public void partaillyUpdateOneData(){

        int newID = lastResponse().jsonPath().getInt("id");
        String Name =  lastResponse().jsonPath().getString("name");
        patchPayload.put("name","Limitless");

        given()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(patchPayload).
        when()
                .patch("/spartans/{id}", newID);
        Ensure.that("The data provided added correctly",
                theResponse->theResponse.statusCode(204));

        given()
                .auth().basic("admin","admin").
        when()
                .get("/spartans/{id}", newID) ;

    }

    @DisplayName("5. Admin Should be able to delete single data")
    @Test
    public void getDeleteData(){

        // cature the id from last get request
        int myId = lastResponse().jsonPath().getInt("id");
        given()
                .auth().basic("admin","admin")
                .pathParam("id", myId).
        when()
                .delete("/spartans/{id}") ;

        Ensure.that("Request is suceess",
                thenResponse->thenResponse.statusCode(204));
        // send another get request to make sure you get 404

        given()
                .auth().basic("admin","admin")
                .pathParam("id", myId).
        when()
                .get("/spartans/{id}") ;

        Ensure.that("Delete was suceess, Can not find data anymore",
                thenResponse->thenResponse.statusCode(404));
    }



}
