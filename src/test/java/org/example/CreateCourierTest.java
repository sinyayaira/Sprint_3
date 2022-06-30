package org.example;

import io.qameta.allure.junit4.DisplayName;
import net.minidev.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends CourierDependTest {

    @Test
    @DisplayName("Check create courier")
    public void createCourier (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", LOGIN);
        requestParams.put("password", PASSWORD);
        requestParams.put("firstName", FIRST_NAME);

        given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier")
            .then().assertThat()
            .statusCode(201)
            .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check create courier without password")
    public void createCourierWithoutPassword (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", LOGIN);

        given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier")
            .then().assertThat()
            .statusCode(400)
            .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check create courier without login")
    public void createCourierWithoutLogin (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("password", PASSWORD);

        given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier")
            .then().assertThat()
            .statusCode(400)
            .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check create courier with login already exist")
    public void createCourierWithLoginAlreadyExist (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", LOGIN);
        requestParams.put("password", PASSWORD);
        requestParams.put("firstName", FIRST_NAME);

        given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier")
            .then().assertThat()
            .statusCode(201)
            .body("ok", equalTo(true));


        given()
            .header("Content-type", "application/json")
            .body(requestParams.toJSONString())
            .post("/api/v1/courier")
            .then().assertThat()
            .statusCode(409)
            .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }
}
