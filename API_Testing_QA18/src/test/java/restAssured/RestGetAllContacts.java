package restAssured;

import com.jayway.restassured.RestAssured;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestGetAllContacts {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWJjQGRlZi5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NDg1OTgyNSwiaWF0IjoxNjg0MjU5ODI1fQ.C6FLMxekSk-GSPCCumQjNwcjZVRI8nX5TpujSzaaUls";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContacts(){

        GetAllContactsDTO list = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(GetAllContactsDTO.class);

        for(ContactDTO contact : list.getContacts()){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("**********************");
        }
    }

}
