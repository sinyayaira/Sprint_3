import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Create_courier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createCourier (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", "monkey");
        requestParams.put("password", "111111");
        requestParams.put("firstName", "vasily");

        given()
                .header("Content-type", "application/json")
                .body(requestParams.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createCourierWithoutPassword (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", "monkey");

        given()
                .header("Content-type", "application/json")
                .body(requestParams.toJSONString())
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithLoginAlreadyExist (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", "monkey");
        requestParams.put("password", "111111");
        requestParams.put("firstName", "vasily");

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
                .body("message", equalTo("Этот логин уже используется"));

    }
}
