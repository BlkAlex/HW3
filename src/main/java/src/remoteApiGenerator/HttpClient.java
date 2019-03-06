package src.remoteApiGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private static final String API_URL = "https://randus.org/api.php";
    private static final String GET = "GET";
    private final JsonParser jsonParser;

    public HttpClient(){
        jsonParser = new JsonParser();
    }

//    public UserPojo readUserInfo(long userId) throws IOException, JSONException {
//        String requestUrl = "https://api.twitter.com/1.1/users/show.json?user_id=" + userId;
//
//        URL url = new URL(requestUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        // add auth header to request
//
//        connection.connect();
//
//        InputStream in;
//        int status = connection.getResponseCode();
//        if (status != HttpURLConnection.HTTP_OK) {
//            in = connection.getErrorStream();
//        } else {
//            in = connection.getInputStream();
//        }
//
//        String response = convertStreamToString(in);
//        UserPojo user = jsonParser.getUser(response);
//
//        return user;
//    }

    private String convertStreamToString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }
}
