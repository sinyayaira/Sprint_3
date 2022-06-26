import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class Login_courier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void authorization (){
        JSONObject requestParams = new JSONObject();
        requestParams.put("login", "monkey");
        requestParams.put("password", "111111");

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
                .post("/api/v1/courier/login")
                .then().assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void authorizationWithWrongLogin() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", "monkey");
        requestParamsOne.put("password", "111111");

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", "apple");
        requestParamsTwo.put("password", "111111");

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
    public void authorizationWithoutPassword() {
        JSONObject requestParamsOne = new JSONObject();
        requestParamsOne.put("login", "monkey");
        requestParamsOne.put("password", "111111");

        JSONObject requestParamsTwo = new JSONObject();
        requestParamsTwo.put("login", "monkey");

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
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
