package restAssured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RestDeleteByID {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWJjQGRlZi5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NDg1OTgyNSwiaWF0IjoxNjg0MjU5ODI1fQ.C6FLMxekSk-GSPCCumQjNwcjZVRI8nX5TpujSzaaUls";

    String id;

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = (int)(System.currentTimeMillis()/1000)%3600;

        ContactDTO contactDTO = ContactDTO.builder()
                .name("John_" + i)
                .lastName("Silver")
                .email("john" + "_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Haifa")
                .description("Pirate")
                .build();

        String message = given()
                .header("Authorization", token)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract()
                .path("message");

        id = message.substring(message.lastIndexOf(' ') + 1);
    }

    @Test
    public void deleteById(){
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was deleted!"));
    }
    @Test
    public void deleteByIdNegative(){
        token = "qwerty";
        given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401);
    }

}