package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
class GreetingResourceTest {
    
    @Test
    void testHelloEndpoint() {
        Response response = given()
                .contentType("application/json")   // 👈 tell Quarkus it's JSON              
                .body("{\"key\":\"value\"}")
                .when().post("/hello");

        // check HTTP 200
        response.then().statusCode(200);

        Log.info(response.getHeaders().asList());
        // get all Content-Encoding headers (including duplicates)
        var transferEncodingHeaders = response.getHeaders().getValues("transfer-encoding");

        Log.info(transferEncodingHeaders);

        // assert that we got duplicates
        assertTrue(transferEncodingHeaders.size() < 2, 
            "Expected max. one transfer-encoding header but got: " + transferEncodingHeaders);
    }

}