package okhttptests;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactDTOResponse;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHTTPDeleteByID {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWJjQGRlZi5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NDg1OTgyNSwiaWF0IjoxNjg0MjU5ODI1fQ.C6FLMxekSk-GSPCCumQjNwcjZVRI8nX5TpujSzaaUls";

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String id;

    @BeforeMethod
    public void preCondition() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;

        ContactDTO contactDTO = ContactDTO.builder()
                .name("John_" + i)
                .lastName("Silver")
                .email("john" + "_" + i + "@mail.com")
                .phone("1234567" + i)
                .address("Haifa")
                .description("Pirate")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ContactDTOResponse contactDTOResponse = gson.fromJson(response.body().string(), ContactDTOResponse.class);
        String message = contactDTOResponse.getMessage();
        id = message.substring(message.lastIndexOf(' ') + 1);
    }

    @Test
    public void deleteByIDTest() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        ContactDTOResponse contactDTOResponse = gson.fromJson(response.body().string(), ContactDTOResponse.class);

        String message = contactDTOResponse.getMessage();
        System.out.println(message);
 }
}