package src;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Jiang Jining
 * @date 2020/10/28 20:52
 */
public class RequestApiTest {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://localhost:8808/test";
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(Objects.requireNonNull(response.body()).toString());
        }
    }
}
