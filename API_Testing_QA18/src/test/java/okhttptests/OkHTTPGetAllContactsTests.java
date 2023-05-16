package okhttptests;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHTTPGetAllContactsTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWJjQGRlZi5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY4NDg1ODI3NiwiaWF0IjoxNjg0MjU4Mjc2fQ.5gPTBREzE5zqIjbXrlFpMkcge_m15-J4II70Ns9Qdrc";

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContacts() throws IOException {
        Request request = new Request.Builder()
                .url("http://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDTO contacts = gson.fromJson(response.body().string(), GetAllContactsDTO.class);

        for (ContactDTO contact : contacts.getContacts()){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("**********");
        }

    }
}
