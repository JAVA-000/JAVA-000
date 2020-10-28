package practice2_2;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class OkHttpDemo {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://localhost:8088/api/hello")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            System.out.println(Objects.requireNonNull(response.body()).string());//hello world
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
