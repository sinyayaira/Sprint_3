package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;

public class CourierDependTest {
    public static final String LOGIN = "monkey";
    public static final String PASSWORD = "111111";
    public static final String FIRST_NAME = "vasily";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        cleanup();
    }

    @After
    public void cleanUpEach() {
        cleanup();
    }

    public static void cleanup() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", LOGIN);
        requestParams.put("password", PASSWORD);

        Response response = RestAssured.given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier/login");

        JsonPath jsonPath = response.jsonPath();
        Integer id = jsonPath.get("id");
        if (id != null) {
            RestAssured.given()
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}");
        }
    }
}

