package okhttptests;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class OkHTTPRegistrationTests {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void registrationTest() throws IOException {
        int i = new Random().nextInt(1000) + 1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("abc_" + i + "@def.com")
                .password("$Abcdef12345")
                .build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        String responseJson = response.body().string();
        AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }
}