package src.remoteApiGenerator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiReader {
    public static String get() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(1, TimeUnit.SECONDS).build();

        Request request = new Request.Builder()
                .url("https://randus.org/api.php")
                .build();
        Response response;
            response = client.newCall(request).execute();
            System.out.println(response.message());
        if (response.body() != null) {
            return response.body().string();
        }
        return "";
    }

}
