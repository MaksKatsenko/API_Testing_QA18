package okhttptests;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactDTOResponse;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHTTPAddNewContactTests {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWJjQGRlZi5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NDg1ODI3NiwiaWF0IjoxNjg0MjU4Mjc2fQ.5gPTBREzE5zqIjbXrlFpMkcge_m15-J4II70Ns9Qdrc";
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void addNewContactTest() throws IOException {

        int i = (int)(System.currentTimeMillis()/1000)%3600;

        ContactDTO contactDTO = ContactDTO.builder()
                .name("John")
                .lastName("Silver")
                .email("john" + i + "@mail.com")
                .phone("3409564" + i)
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
        Assert.assertTrue(response.isSuccessful());
        ContactDTOResponse contactDTOResponse = gson.fromJson(response.body().string(), ContactDTOResponse.class);
        String message = contactDTOResponse.getMessage();
        Assert.assertTrue(message.contains("Contact was added!"));
        String id = message.substring(message.lastIndexOf(' ') + 1);
        System.out.println(id);
    }


}