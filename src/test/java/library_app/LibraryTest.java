package library_app;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serenity.Utility.ConfigReader;

import static io.restassured.RestAssured.*;

@SerenityTest
public class LibraryTest {

//    private EnvironmentVariables environmentVariables ;
        @Steps
        ConfigReader conf ;

//    @BeforeAll
//    public static void setUp(){
//        RestAssured.baseURI = "http://library1.cybertekschool.com";
//        RestAssured.basePath = "/rest/v1" ;
//    }
    @BeforeEach
    public void setUpEach(){
        RestAssured.baseURI  = conf.getProperty("base.url") ;
        RestAssured.basePath = conf.getProperty("base.path") ;
    }

    @AfterAll
    public static void tearDown(){
        RestAssured.reset();
        SerenityRest.clear();
    }

    @Test
    public void testLogin(){
        given()
                .log().all()
                .contentType( ContentType.URLENC  )
                .formParam("email", conf.getProperty("librarian.username"))
                .formParam("password",conf.getProperty("librarian.password")).
                when()
                .post("/login") ;

        Ensure.that("Getting Successful Result",
                vRes -> vRes.statusCode(200)
        ) ;
    }

    @Test
    public void testingReadingConfigFile(){

        System.out.println("conf.getProperty(\"base.url\") = "
                + conf.getProperty("base.url"));
        System.out.println("conf.getProperty(\"librarian.username\") = "
                + conf.getProperty("librarian.username"));

    }


}


