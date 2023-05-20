package restAssured;

import com.jayway.restassured.RestAssured;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RestLoginTests {

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginPositiveTest(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("abc@def.com")
                .password("$Abcdef12345")
                .build();

        AuthResponseDTO responseDTO = given()
                .body(requestDTO)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponseDTO.class);

        System.out.println(responseDTO.getToken());
    }

    @Test
    public void loginNegativeWrongEmailTest(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("abcdef.com")
                .password("$Abcdef12345")
                .build();

        ErrorDTO errorDTO = given()
                .body(requestDTO)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDTO.class);

        Object message = errorDTO.getMessage();
        System.out.println(message);
        Assert.assertEquals(message, "Login or Password incorrect");
    }

    @Test
    public void loginNegativeWrongPasswordTest(){
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("abc@def.com")
                .password("Abcdef12345")
                .build();

        given()
                .body(requestDTO)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("Login or Password incorrect"));
    }
}