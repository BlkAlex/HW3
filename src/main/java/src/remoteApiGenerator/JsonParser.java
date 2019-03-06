package src.remoteApiGenerator;

import java.io.FileInputStream;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
public class JsonParser {

    public static UserPojo getHuman(String response) throws IOException {

        //String testJsonString = "{\"lname\":\"Шмидт\",\"fname\":\"Федор\",\"patronymic\":\"Данилович\",\"gender\":\"m\",\"date\":\"15 апреля 1985\",\"postcode\":\"452995\",\"city\":\"Челно-Вершины\",\"street\":\"Трофимова\",\"house\":27,\"apartment\":79,\"phone\":\"8-944-571-24-60\",\"login\":\"ShmidtFedor249\",\"password\":\"pR3XIwM7gCpK\",\"color\":\"Чёрный\",\"userpic\":\"https://randus.org/avatars/m/myAvatar14.png\"}";
        ObjectMapper mapper = new ObjectMapper();
        UserPojo user = (UserPojo) mapper.readValue(response,UserPojo.class);
        System.out.println(response);
        return user;
//        long id = userJson.getLong("id");
//        String name = userJson.getString("name");
//        String nick = userJson.getString("screen_name");
//        String location = userJson.getString("location");
//        String description = userJson.getString("description");
//        String imageUrl = userJson.getString("profile_image_url");
//        int followersCount = userJson.getInt("followers_count");
//        int followingCount = userJson.getInt("favourites_count");
//
//        return new User(id, imageUrl, name, nick, description, location, followingCount, followersCount);
    }
}