package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check get list of orders")
    public void getListOfOrders () {
        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then().assertThat()
                .statusCode(200)
                .body("orders", notNullValue());

    }
}
