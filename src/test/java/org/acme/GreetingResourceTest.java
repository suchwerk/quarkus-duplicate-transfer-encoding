package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
class GreetingResourceTest {
    /**
     * This test calls /hello/delegate
     * The endpoint generates a large JSON payload internally
     * and returns it. It should not produce duplicate Transfer-Encoding headers.
     */
    @Test
    void testDelegateEndpoint() {
        Response response = given()
                .contentType("application/json")
                .body("{\"key\":\"value\"}")
                .when().post("/hello/delegate");

        // check HTTP 200
        response.then().statusCode(200);

        Log.info(response.getHeaders().asList());
        
        // get all Content-Encoding headers (including duplicates)
        var transferEncodingHeaders = response.getHeaders().getValues("Transfer-Encoding");

        Log.info(transferEncodingHeaders);

        // assert that we got duplicates
        assertTrue(transferEncodingHeaders.size() == 1,
                "Expected one transfer-encoding header but got: " + transferEncodingHeaders);
    }

    /**
     * This test calls /hello
     * The endpoint calls /hello/delegate via a restclient and returns the response.
     * It may produce duplicate Transfer-Encoding headers.
     */
    @Test
    void testHelloEndpoint() {
        Response response = given()
                .contentType("application/json")
                .body("{\"key\":\"value\"}")
                .when().post("/hello");

        // check HTTP 200
        response.then().statusCode(200);

        Log.info(response.getHeaders().asList());

        // get all Content-Encoding headers (including duplicates)
        var transferEncodingHeaders = response.getHeaders().getValues("Transfer-Encoding");

        Log.info(transferEncodingHeaders);

        // assert that we got duplicates
        assertTrue(transferEncodingHeaders.size() == 1,
                "Expected one transfer-encoding header but got: " + transferEncodingHeaders);
    }

}