import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.*;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Tests {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withRequestSpec() {

        given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().
                assertThat().
                statusCode(200);
    }

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withResponseSpec() {

        given().
                spec(requestSpec).
                when().
                get("http://zippopotam.us/us/90210").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills() {

        String placeName =

                given().
                        spec(requestSpec).
                        when().
                        get("http://zippopotam.us/us/90210").
                        then().
                        extract().
                        path("places[0].'place name'");

        Assert.assertEquals("Beverly Hills", placeName);
    }
    @Test
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                contentType(ContentType.JSON);
    }

    @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails() {

        given().
                log().all().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                log().body();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkStateNameInResponseBody_expectCalifornia() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].state", equalTo("California"));
    }

    @Test
    public void requestUsZipCode90210_checkListOfPlaceNamesInResponseBody_expectContainsBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasItem("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkNumberOfPlaceNamesInResponseBody_expectOne() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places.'place name'", hasSize(1));
    }

}


