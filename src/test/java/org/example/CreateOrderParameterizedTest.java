package org.example;

import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameter(0)
    public String firstName;

    @Parameterized.Parameter(1)
    public String lastName;

    @Parameterized.Parameter(2)
    public String address;

    @Parameterized.Parameter(3)
    public String metroStation;

    @Parameterized.Parameter(4)
    public String phone;

    @Parameterized.Parameter(5)
    public String rentTime;

    @Parameterized.Parameter(6)
    public String deliveryDate;

    @Parameterized.Parameter(7)
    public String comment;

    @Parameterized.Parameter(8)
    public String [] color;

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                {"ма", "на", "адрес", "10", "77777777777", "6", "2020-06-06", "comment", new String[]{"BLACK"}},
                {"длинноеимя", "длиннаяфамилия", "оченьдлинныйадрес", "20", "+7777777777", "1", "2020-06-06", "", new String[]{"GREY"}},
                {"имя с пробелами", "фамилия", "адрес с пробелами", "10", "8777777777", "6", "2020-06-06", "", new String[]{"BLACK", "GREY"}},
                {"ма", "на", "адрес с 3, и со знаками.", "10", "00000000000", "6", "2020-06-06", "comment", new String[]{}},
        };
    }

    @Test
    public void createOrder() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("firstName", firstName);
        requestParams.put("lastName", lastName);
        requestParams.put("address", address);
        requestParams.put("metroStation", metroStation);
        requestParams.put("phone", phone);
        requestParams.put("rentTime", rentTime);
        requestParams.put("deliveryDate", deliveryDate);
        requestParams.put("comment", comment);
        requestParams.put("color", color);

        given()
                .header("Content-type", "application/json")
                .body(requestParams.toJSONString())
                .post("/api/v1/orders")
                .then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());

    }
}
