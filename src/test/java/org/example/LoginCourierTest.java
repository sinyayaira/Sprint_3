package org.example;

import io.qameta.allure.junit4.DisplayName;
import net.minidev.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends CourierDependTest {

    @Test
    @DisplayName("Check authorization")
    public void authorization (){
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", LOGIN);
        requestParamsOne.put("password", PASSWORD);
        requestParamsOne.put("firstName", FIRST_NAME);

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", LOGIN);
        requestParamsTwo.put("password", PASSWORD);

        given()
                .header("Content-type", "application/json")
                .body(requestParamsOne.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .header("Content-type", "application/json")
                .body(requestParamsTwo.toJSONString())
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Check authorization with wrong login")
    public void authorizationWithWrongLogin() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", LOGIN);
        requestParamsOne.put("password", PASSWORD);
        requestParamsOne.put("firstName", FIRST_NAME);

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", "apple");
        requestParamsTwo.put("password", PASSWORD);

        given()
                .header("Content-type", "application/json")
                .body(requestParamsOne.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .header("Content-type", "application/json")
                .body(requestParamsTwo.toJSONString())
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check authorization with wrong password")
    public void authorizationWithWrongPassword() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", LOGIN);
        requestParamsOne.put("password", PASSWORD);
        requestParamsOne.put("firstName", FIRST_NAME);

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", LOGIN);
        requestParamsTwo.put("password", "222222");

        given()
                .header("Content-type", "application/json")
                .body(requestParamsOne.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .header("Content-type", "application/json")
                .body(requestParamsTwo.toJSONString())
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check authorization without password")
    public void authorizationWithoutPassword() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", LOGIN);
        requestParamsOne.put("password", PASSWORD);

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", LOGIN);

        given()
                .header("Content-type", "application/json")
                .body(requestParamsOne.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        System.out.println("end of create");

        given()
                .header("Content-type", "application/json")
                .body(requestParamsTwo.toJSONString())
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        System.out.println("end");
    }

    @Test
    @DisplayName("Check authorization without login")
    public void authorizationWithoutLogin() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", LOGIN);
        requestParamsOne.put("password", PASSWORD);

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("password", PASSWORD);

        given()
                .header("Content-type", "application/json")
                .body(requestParamsOne.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        given()
                .header("Content-type", "application/json")
                .body(requestParamsTwo.toJSONString())
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
